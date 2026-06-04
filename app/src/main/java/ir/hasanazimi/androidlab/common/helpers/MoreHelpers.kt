package ir.hasanazimi.androidlab.common.helpers

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ir.hasanazimi.androidlab.common.helpers.extension_helpers.isAppAvailable
import ir.hasanazimi.androidlab.common.helpers.extension_helpers.showToast
import java.util.Locale

/**
 * Util class for converting between dp, px and other magical pixel units
 */
object PixelHelper {
    @JvmStatic
    fun dpToPx(dp: Float, context: Context): Int {
        return dpToPx(context, dp)
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int {
        return dpToPx(context, dp.toFloat())
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Float): Int {
        return Math.round(dp * getPixelScaleFactor(context))
    }

    @JvmStatic
    fun spToPx(sp: Float, @NonNull context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun pxToDp(px: Float, context: Context): Float {
        return px / getPixelScaleFactor(context)
    }

    private fun getPixelScaleFactor(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
    }

    @JvmStatic
    fun getWidth(@NonNull context: Context): Int {
        return getWidthPx(context)
    }

    @JvmStatic
    fun getWidthPx(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    @JvmStatic
    fun getHeight(@NonNull context: Context): Int {
        return getHeightPx(context)
    }

    @JvmStatic
    fun getHeightPx(@NonNull context: Context): Int {
        return getHeightPx_2(context)
    }

    @JvmStatic
    fun getHeightPx_2(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }
}

class IntentActionsHelper(private val activity: Activity) {
    fun callPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        activity.startActivity(intent)
    }

    fun sendMessageToPhoneNumber(phoneNumber: String, msg: String) {
        val intentSMS = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber"))
        intentSMS.putExtra("sms_body", msg)
        activity.startActivity(intentSMS)
    }

    fun openWebSite(url: String) {
        if (url.isEmpty()) return
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun goToLocation(latitude: String, longitude: String) {
        val uri = Uri.parse("google.navigation:q=$latitude,$longitude&mode=d")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")
        activity.startActivity(intent)
    }

    fun openLinkedInPage(linkedId: String) {
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@$linkedId"))
        val packageManager = activity.packageManager
        val list: List<ResolveInfo> = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.isEmpty()) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=$linkedId"))
        }
        activity.startActivity(intent)
    }

    fun sendEmail(email: String, subject: String = "", text: String = "") {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "message/rfc822"
        intent.data = Uri.parse("mailto:$email")
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        activity.startActivity(Intent.createChooser(intent, "Send mail..."))
    }

    fun openInstagram(instagramID: String) {
        val uri = Uri.parse("http://instagram.com/_u/$instagramID")
        val likeIng = Intent(Intent.ACTION_VIEW, uri)
        likeIng.setPackage("com.instagram.android")
        try {
            activity.startActivity(likeIng)
        } catch (e: ActivityNotFoundException) {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/$instagramID")))
        }
    }

    fun shareMessageToTelegram(msg: String) {
        val appName = "org.telegram.messenger"
        val isAppInstalled = isAppAvailable(activity, appName)
        if (isAppInstalled) {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            myIntent.setPackage(appName)
            myIntent.putExtra(Intent.EXTRA_TEXT, msg)
            activity.startActivity(Intent.createChooser(myIntent, "Share with"))
        } else {
            Toast.makeText(activity, "Telegram not Installed", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareContent(message: String, url: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "$message\n$url")
            type = "text/plain"
        }
        activity.startActivity(Intent.createChooser(shareIntent, "اشتراک با"))
    }


    fun sendUserToApplicationSetting(applicationPackageName : String){
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            "package:$applicationPackageName".toUri()
        )
        activity.startActivity(intent)
    }
}

class NotificationHelper(private val context: Context) {
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
            PendingIntent.getActivity(
                context, 0, it,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(priority)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showToast(context, "Permissions not allowed")
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

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
            context, 0, actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(0, actionText, actionPendingIntent)
            .setAutoCancel(false)
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showToast(context, "Permissions not allowed")
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

    fun showProgressNotification(
        channelId: String,
        title: String,
        icon: Int,
        progressMax: Int,
        progressCurrent: Int,
        notificationId: Int = 3
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(progressMax, progressCurrent, false)
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showToast(context, "Permissions not allowed")
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

    fun updateProgressNotification(
        channelId: String,
        title: String,
        icon: Int,
        progressMax: Int,
        progressCurrent: Int,
        notificationId: Int
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(progressMax, progressCurrent, false)
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showToast(context, "Permissions not allowed")
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

    fun showHighPriorityNotification(
        channelId: String,
        title: String,
        message: String,
        icon: Int,
        notificationId: Int = 4
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showToast(context, "Permissions not allowed")
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

    fun showBigPictureNotification(
        channelId: String,
        title: String,
        message: String,
        bigPicture: Bitmap,
        icon: Int,
        notificationId: Int = 5
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bigPicture))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(false)
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showToast(context, "Permissions not allowed")
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }
}

class LocationHelper(private val context: Context) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLastLocation(
        onSuccess: (Location) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onFailure.invoke(SecurityException("Location permissions not granted"))
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    onSuccess.invoke(it)
                } ?: run {
                    onFailure.invoke(Exception("Last known location not found"))
                }
            }
            .addOnFailureListener { e ->
                onFailure.invoke(e)
            }
    }
}










const val PERSIAN_LANGUAGE_CODE = "fa"
const val PERSIAN_COUNTRY_CODE = "IR"
const val ENGLISH_LANGUAGE_CODE = "en"
const val ENGLISH_COUNTRY_CODE = "US"
const val LOCALE_INIT = "locale_init"
const val BUBBLE_CARD_GUID = "card_guid_bubble"
const val SHOW_CARD_GUID_LAYOUT = "show_guid_card_layout"

fun localizedContext(baseContext: Context, locale: Locale = Locale(ENGLISH_LANGUAGE_CODE)): Context {
    Locale.setDefault(locale)
    val configuration = baseContext.resources.configuration
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    return baseContext.createConfigurationContext(configuration)
}
