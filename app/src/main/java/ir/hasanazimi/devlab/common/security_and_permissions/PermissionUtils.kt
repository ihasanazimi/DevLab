package ir.hasanazimi.devlab.common.security_and_permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {


    fun requestPermissions(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int,
        callback: PermissionResultCallback
    ) {
        if (arePermissionsGranted(activity, permissions)) {
            callback.onPermissionGranted()
        } else {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
            PermissionResultHandler.registerCallback(requestCode, callback)
        }
    }

    fun arePermissionsGranted(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        PermissionResultHandler.handleResult(requestCode, permissions, grantResults)
    }

    interface PermissionResultCallback {
        fun onPermissionGranted()
        fun onPermissionDenied()
    }

    private object PermissionResultHandler {
        private val callbackMap = mutableMapOf<Int, PermissionResultCallback>()

        fun registerCallback(requestCode: Int, callback: PermissionResultCallback) {
            callbackMap[requestCode] = callback
        }

        fun handleResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            val callback = callbackMap[requestCode]
            if (callback != null) {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    callback.onPermissionGranted()
                } else {
                    callback.onPermissionDenied()
                }
                callbackMap.remove(requestCode)
            }
        }
    }

    /** Usage */
    /**
    PermissionUtils.requestPermissions(
    this,
    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
    requestCode,
    object : PermissionUtils.PermissionResultCallback {

    override fun onPermissionGranted() {
    // Permissions granted, proceed with your functionality
    }

    override fun onPermissionDenied() {
    // Permissions denied, show a message to the user
    }

    })

     * */


}
