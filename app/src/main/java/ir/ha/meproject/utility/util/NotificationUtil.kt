package ir.ha.meproject.utility.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ir.ha.meproject.utility.extensions.isTIRAMISUPlus
import ir.ha.meproject.utility.extensions.showToast
import ir.ha.meproject.utility.security.PermissionUtils

class NotificationUtil(private val activity: Activity) {

    @SuppressLint("MissingPermission")
    fun showBasicNotification(
        channelId: String,
        title: String,
        message: String,
        icon: Int,
        priority: Int = NotificationCompat.PRIORITY_DEFAULT,
        intent: Intent? = null,
        notificationId: Int = 1
    ) {
        val pendingIntent: PendingIntent? = intent?.let {
            PendingIntent.getActivity(activity, 0, it, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(activity, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(activity)


        if (isTIRAMISUPlus()){
            if (PermissionUtils.arePermissionsGranted(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS)).not()) {
                PermissionUtils.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS),2024,object : PermissionUtils.PermissionResultCallback{
                    override fun onPermissionGranted() {
                        showToast(activity,"Permission Granted")
                    }

                    override fun onPermissionDenied() {
                        showToast(activity,"Permission Denied")
                    }
                })
                return
            }
        }

        notificationManager.notify(notificationId, builder.build())
    }

    @SuppressLint("MissingPermission")
    fun showNotificationWithAction(
        channelId: String,
        title: String,
        message: String,
        icon: Int,
        actionText: String,
        actionIntent: Intent,
        notificationId: Int = 2
    ) {
        val actionPendingIntent: PendingIntent = PendingIntent.getActivity(
            activity, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(activity, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(0, actionText, actionPendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(activity)


        if (isTIRAMISUPlus()){
            if (PermissionUtils.arePermissionsGranted(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS)).not()) {
                PermissionUtils.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS),2024,object : PermissionUtils.PermissionResultCallback{
                    override fun onPermissionGranted() {
                        showToast(activity,"Permission Granted")
                    }

                    override fun onPermissionDenied() {
                        showToast(activity,"Permission Denied")
                    }
                })
                return
            }
        }

        notificationManager.notify(notificationId, builder.build())
    }

    @SuppressLint("MissingPermission")
    fun showProgressNotification(
        channelId: String,
        title: String,
        icon: Int,
        progressMax: Int,
        progressCurrent: Int,
        notificationId: Int = 3
    ) {
        val builder = NotificationCompat.Builder(activity, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(progressMax, progressCurrent, false)

        val notificationManager = NotificationManagerCompat.from(activity)


        if (isTIRAMISUPlus()){
            if (PermissionUtils.arePermissionsGranted(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS)).not()) {
                PermissionUtils.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS),2024,object : PermissionUtils.PermissionResultCallback{
                    override fun onPermissionGranted() {
                        showToast(activity,"Permission Granted")
                    }

                    override fun onPermissionDenied() {
                        showToast(activity,"Permission Denied")
                    }
                })
                return
            }
        }

        notificationManager.notify(notificationId, builder.build())
    }

    @SuppressLint("MissingPermission")
    fun updateProgressNotification(
        channelId: String,
        title: String,
        icon: Int,
        progressMax: Int,
        progressCurrent: Int,
        notificationId: Int
    ) {
        val builder = NotificationCompat.Builder(activity, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(progressMax, progressCurrent, false)

        val notificationManager = NotificationManagerCompat.from(activity)


        if (isTIRAMISUPlus()){
            if (PermissionUtils.arePermissionsGranted(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS)).not()) {
                PermissionUtils.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS),2024,object : PermissionUtils.PermissionResultCallback{
                    override fun onPermissionGranted() {
                        showToast(activity,"Permission Granted")
                    }

                    override fun onPermissionDenied() {
                        showToast(activity,"Permission Denied")
                    }
                })
                return
            }
        }

        notificationManager.notify(notificationId, builder.build())
    }

    @SuppressLint("MissingPermission")
    fun showHighPriorityNotification(
        channelId: String,
        title: String,
        message: String,
        icon: Int,
        notificationId: Int = 4
    ) {
        val builder = NotificationCompat.Builder(activity, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(activity)


        if (isTIRAMISUPlus()){
            if (PermissionUtils.arePermissionsGranted(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS)).not()) {
                PermissionUtils.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS),2024,object : PermissionUtils.PermissionResultCallback{
                    override fun onPermissionGranted() {
                        showToast(activity,"Permission Granted")
                    }

                    override fun onPermissionDenied() {
                        showToast(activity,"Permission Denied")
                    }
                })
                return
            }
        }

        notificationManager.notify(notificationId, builder.build())
    }

    @SuppressLint("MissingPermission")
    fun showBigPictureNotification(
        channelId: String,
        title: String,
        message: String,
        bigPicture: Bitmap,
        icon: Int,
        notificationId: Int = 5
    ) {
        val builder = NotificationCompat.Builder(activity, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bigPicture))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(activity)


        if (isTIRAMISUPlus()){
            if (PermissionUtils.arePermissionsGranted(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS)).not()) {
                PermissionUtils.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS),2024,object : PermissionUtils.PermissionResultCallback{
                    override fun onPermissionGranted() {
                        showToast(activity,"Permission Granted")
                    }

                    override fun onPermissionDenied() {
                        showToast(activity,"Permission Denied")
                    }
                })
                return
            }
        }

        notificationManager.notify(notificationId, builder.build())
    }
}
