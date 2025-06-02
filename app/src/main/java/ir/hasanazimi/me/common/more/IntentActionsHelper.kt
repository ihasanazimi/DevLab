package ir.hasanazimi.me.common.more

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.widget.Toast
import ir.hasanazimi.me.R
import ir.hasanazimi.me.common.extensions.isAppAvailable

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

    fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "message/rfc822"
        intent.data = Uri.parse("mailto:$email")
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
        intent.putExtra(Intent.EXTRA_TEXT, "")
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


}
