package ir.hasanazimi.devlab.common.security_and_permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val PERMISSION_REQUEST_CODE = 10010


fun Activity.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.shouldShowRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}


fun Activity.askPermission(
    permission: String,
    requestCode: Int = PERMISSION_REQUEST_CODE,
    onPermissionAlreadyGranted: () -> Unit = {},
    onShowRationale: () -> Unit = {},
    onRequest: () -> Unit = {}
) {
    if (isPermissionGranted(permission)) {
        onPermissionAlreadyGranted()
    } else {
        if (shouldShowRationale(permission)) {
            // کاربر رد کرده قبلاً، باید براش توضیح بدی
            onShowRationale()
        } else {
            // مستقیم درخواست بده
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            onRequest()
        }
    }
}


/** result
 *
 *
 *override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
 *     when (requestCode) {
 *         PERMISSION_REQUEST_CODE -> {
 *             if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 *                 // Permission granted, handle accordingly
 *             } else {
 *                 // Permission denied, handle accordingly
 *             }
 *             return
 *         }
 *         // Handle other permission request codes if needed
 *     }
 *     super.onRequestPermissionsResult(requestCode, permissions, grantResults)
 * }
 *
 *
 * */


/** result
 *
 *
 *override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
 *     when (requestCode) {
 *         PERMISSION_REQUEST_CODE -> {
 *             if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 *                 // Permission granted, handle accordingly
 *             } else {
 *                 // Permission denied, handle accordingly
 *             }
 *             return
 *         }
 *         // Handle other permission request codes if needed
 *     }
 *     super.onRequestPermissionsResult(requestCode, permissions, grantResults)
 * }
 *
 *
 * */