package ir.hasanazimi.androidlab.common.helpers


import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import java.io.File
import java.security.Key
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Utility class for Base64 encoding and decoding
 */
class Base64Util {
    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptBase64(data: String): String {
        val encodedBytes = Base64.getEncoder().encodeToString(data.toByteArray())
        return encodedBytes
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptBase64(encodedData: String): String {
        val decodedBytes = Base64.getDecoder().decode(encodedData)
        return String(decodedBytes)
    }
}

/**
 * Utility class for AES encryption and decryption
 */
class AESCryptor(private val key: String) {
    private val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    private val secretKey: Key = SecretKeySpec(key.toByteArray(), "AES")

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(data: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(encryptedData: String): String {
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decryptedBytes)
    }
}

/**
 * Top-level function to encode a file to Base64
 */
@RequiresApi(Build.VERSION_CODES.O)
fun encodeFileToBase64(file: File): String {
    val bytes = file.readBytes()
    return Base64.getEncoder().encodeToString(bytes)
}

/**
 * Extension functions for Activity to handle permissions
 */
private const val PERMISSION_REQUEST_CODE = 10010

fun Activity.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.shouldShowRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}


private var permissionCallbacks: MutableMap<Int, PermissionCallback> = mutableMapOf()

data class PermissionCallback(
    val permission: String,
    val onGranted: () -> Unit,
    val onDenied: () -> Unit,
    val onPermanentlyDenied: () -> Unit
)

fun Activity.askPermission(
    permission: String,
    requestCode: Int = PERMISSION_REQUEST_CODE,
    onPermissionAlreadyGranted: () -> Unit = {},
    onShowRationale: () -> Unit = {},
    onPermissionDenied: () -> Unit = {},
    onPermissionPermanentlyDenied: () -> Unit = {}
) {
    val prefs = getSharedPreferences("perm_prefs", MODE_PRIVATE)
    val isFirstTimePassed = !prefs.getBoolean(permission, false)

    when {
        isPermissionGranted(permission) -> {
            onPermissionAlreadyGranted()
        }

        shouldShowRationale(permission) -> {
            onShowRationale()
        }

        !isFirstTimePassed -> {
            // یعنی قبلاً درخواست دادی و الان rationale هم false هست → permanently denied
            onPermissionPermanentlyDenied()
        }

        else -> {
            prefs.edit { putBoolean(permission, true) }

            permissionCallbacks[requestCode] = PermissionCallback(
                permission,
                onPermissionAlreadyGranted,
                onPermissionDenied,
                onPermissionPermanentlyDenied
            )

            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }
}


fun Activity.handlePermissionResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    val callback = permissionCallbacks[requestCode] ?: return

    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        callback.onGranted()
    } else {
        if (!shouldShowRequestPermissionRationale(callback.permission)) {
            callback.onPermanentlyDenied()
        } else {
            callback.onDenied()
        }
    }

    permissionCallbacks.remove(requestCode)
}


/**
 * Usage example:



override fun onRequestPermissionsResult(
requestCode: Int,
permissions: Array<out String>,
grantResults: IntArray
) {
super.onRequestPermissionsResult(requestCode, permissions, grantResults)

handlePermissionResult(requestCode, permissions, grantResults)
}


 */