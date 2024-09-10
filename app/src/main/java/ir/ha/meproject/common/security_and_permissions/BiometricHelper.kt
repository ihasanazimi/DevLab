package ir.ha.meproject.common.security_and_permissions

import android.app.KeyguardManager
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import ir.ha.meproject.common.extensions.isMarshmallowPlus
import ir.ha.meproject.common.extensions.isNougatPlus
import java.security.Key
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class BiometricHelper(
    private val fragment: Fragment,
    private val callback: BiometricPrompt.AuthenticationCallback
) {

    private val TAG = BiometricHelper::class.java.simpleName

    companion object {
        const val EWANO_BIOMETRIC_KEY = "HASAN_AZIMI_BIOMETRIC_KEY"
        const val androidKeyStore = "AndroidKeyStore"
    }

    var keyStore: KeyStore? = null
    var keyGenerator: KeyGenerator? = null
    var cipher: Cipher? = null
    var secretKey: Key? = null



    fun auth() {

        val keyguardManager =
            fragment.requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            Log.e(TAG, "keyguard not enabled")
            return
        }

        runCatching {
            keyStore = KeyStore.getInstance(androidKeyStore).also {
                Log.i(TAG, "KeyStore.getInstance -> $it ")
            }
        }.onFailure {
            Log.e(TAG, "onFailure - keyStore instance :" + it.message.toString())
            return
        }


        runCatching {
            if (isMarshmallowPlus()) {
                keyGenerator =
                    KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, androidKeyStore)
                        .also {
                            Log.i(TAG, "KeyGenerator.getInstance -> $it ")
                        }
            } else keyGenerator = null
        }.onFailure {
            Log.e(TAG, "onFailure - keyGenerator instance" + it.message.toString())
            return
        }


        runCatching {
            keyStore?.load(null)
            if (isNougatPlus()) {
                keyGenerator?.init(
                    KeyGenParameterSpec.Builder(
                        EWANO_BIOMETRIC_KEY,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setUserAuthenticationRequired(true)
                        .setInvalidatedByBiometricEnrollment(true)
                        .build()
                )
            } else if (isMarshmallowPlus()) {
                keyGenerator?.init(
                    KeyGenParameterSpec.Builder(
                        EWANO_BIOMETRIC_KEY,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setUserAuthenticationRequired(true)
                        .build()
                )
            } else keyGenerator?.init(SecureRandom())
            keyGenerator?.generateKey().also {}
        }.onFailure {
            Log.e(TAG, "onFailure - keyStore instance : " + it.message.toString())
            return
        }


        runCatching {
            if (isMarshmallowPlus()) {
                cipher =
                    Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .also {
                            Log.i(TAG, "Cipher.getInstance -> $cipher")
                        }
            }
        }.onFailure {
            Log.e(TAG, "onFailure - cipher instance :" + it.message.toString())
            return
        }


        runCatching {
            keyStore?.load(null)
            secretKey = keyStore?.getKey(EWANO_BIOMETRIC_KEY, null) as SecretKey
            Log.i(TAG, "keyStore?.getKey -> $secretKey ")
            cipher?.init(Cipher.ENCRYPT_MODE, secretKey)
            Log.i(TAG, "cipher?.init(Cipher.ENCRYPT_MODE, secretKey) is : ${cipher?.provider} and CIPHER IS -> $cipher")
        }.onFailure {
            Log.i(TAG, "onFailure - SecretKey instance :" + it.message.toString())
            return
        }

        val cryptoObject = BiometricPrompt.CryptoObject(cipher!!)

        Log.i(
            TAG,
            "CryptoObject is : ${Gson().toJson(cryptoObject)}   |   SecretKey is : ${
                Gson().toJson(secretKey)
            }"
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("TITLE")
            .setSubtitle("Subtitle")
            .setNegativeButtonText("setNegativeButtonText")
            .setConfirmationRequired(false)
            .setDeviceCredentialAllowed(false)
            .build()

        val mainExecutor = ContextCompat.getMainExecutor(fragment.requireActivity())
        BiometricPrompt(
            fragment,
            mainExecutor,
            callback
        ).authenticate(promptInfo, cryptoObject)
    }

}