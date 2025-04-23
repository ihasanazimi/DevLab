package ir.hasanazimi.me.common.security_and_permissions

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ir.hasanazimi.me.common.extensions.isNougatPlus
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class BiometricUtil(
    private val fragment: Fragment,
    private val callback: BiometricPrompt.AuthenticationCallback
) {

    companion object {
        private const val TAG = "BiometricUtil"
        private const val KEY_NAME = "HASAN_AZIMI_BIOMETRIC_KEY"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private val PASSWORD = charArrayOf('h', 'a', 's', 'a', 'n')
        private const val KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$KEY_ALGORITHM/$BLOCK_MODE/$PADDING"
    }

    private lateinit var keyStore: KeyStore
    private lateinit var cipher: Cipher

    @RequiresApi(Build.VERSION_CODES.M)
    fun createKey() {
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
            if (!keyStore.containsAlias(KEY_NAME)) {
                val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM, ANDROID_KEYSTORE)
                val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                ).apply {
                    setBlockModes(BLOCK_MODE)
                    setEncryptionPaddings(PADDING)
                    setUserAuthenticationRequired(true)
                    setUserAuthenticationValidityDurationSeconds(30) // Optional: require ReaAuthentication after 30s
                    if (isNougatPlus()) setInvalidatedByBiometricEnrollment(true)
                }.build()

                keyGenerator.init(keyGenParameterSpec)
                keyGenerator.generateKey().also {
                    Log.i(TAG, "Key generated: $it")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create key: $e")
            throw RuntimeException("Failed to create key", e)
        }
    }

    private fun initCipher(): Cipher {
        return try {
            cipher = Cipher.getInstance(TRANSFORMATION)
            keyStore.load(null)
            // todo check this logic safely on the android 5
            val secretKey = keyStore.getKey(KEY_NAME, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            cipher
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize cipher: $e")
            throw RuntimeException("Failed to initialize cipher", e)
        }
    }

    fun authenticate() {
        val keyguardManager = fragment.requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            Log.e(TAG, "Keyguard not enabled")
            return
        }

        val cipher = initCipher()
        val cryptoObject = BiometricPrompt.CryptoObject(cipher)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("TITLE")
            .setSubtitle("Subtitle")
            .setNegativeButtonText("setNegativeButtonText")
            .setConfirmationRequired(false)
            .setDeviceCredentialAllowed(false)
            .build()

        val biometricPrompt = BiometricPrompt(
            fragment,
            ContextCompat.getMainExecutor(fragment.requireActivity()),
            callback
        )
        biometricPrompt.authenticate(promptInfo, cryptoObject)
    }
}
