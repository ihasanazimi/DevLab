package ir.ha.meproject.utility.security

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ir.ha.meproject.utility.extensions.isMarshmallowPlus

const val PERMISSION_REQUEST_CODE = 10010


fun checkPermission(activity: Activity, permission: String?): Boolean {
    return if (isMarshmallowPlus()) {
        activity.checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    } else {
        activity.packageManager.checkPermission(
            permission!!,
            activity.packageName
        ) == PackageManager.PERMISSION_GRANTED
    }
}

fun requestPermission(activity: Activity, permission: String) {
    if (isMarshmallowPlus()) { activity.requestPermissions(arrayOf(permission), PERMISSION_REQUEST_CODE) }
} // You can add more methods as needed for handling permission results, etc.


fun Activity.requestPermission(permission: String, requestCode: Int = PERMISSION_REQUEST_CODE) {
    if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
    }
}



fun checkPermission(activity: Activity , permission: String, requestCode: Int) {
    if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
        // Requesting the permission
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    } else {
        Toast.makeText(activity, "Permission already granted", Toast.LENGTH_SHORT).show()
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