package ir.hasanazimi.devlab.common.extensions

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager


fun Context.copyToClipboard(text: String){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Copied Text:", text))
}


fun Context.isEnabledDarkMode() : Boolean{
    val darkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
    return darkModeFlags == Configuration.UI_MODE_NIGHT_YES //Check if the Dark Mode is On
}



fun Context.isDarkThemeOn(context: Context): Boolean {
    Log.i("TAG", "isDarkThemeOn: ${context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK}")
    return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        /* 32 */ Configuration.UI_MODE_NIGHT_YES -> true
        /* 16 */ Configuration.UI_MODE_NIGHT_NO -> false
        /* 0 */ Configuration.UI_MODE_NIGHT_UNDEFINED -> false
        else -> false
    }
}


fun enableTurnScreenOnAlwaysFlag(window : Window, enable : Boolean){
    if (enable) window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
    else window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
}


fun turnOnGPS(activity: Activity) {
    val manager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER).not()) {
        // turnOnGPS
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity.startActivity(intent)
    }
}



fun View.findLocationOfCenterOnTheScreen(): IntArray {
    val positions = intArrayOf(0, 0)
    getLocationInWindow(positions)
    // Get the center of the view
    positions[0] = positions[0] + width / 2
    positions[1] = positions[1] + height / 2
    return positions
}



fun hideKeyboard(view: View?) {
    val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun showKeyboard(view: View?) {
    view?.requestFocus()
    val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}


fun isAppAvailable(context: Context, appName: String): Boolean {
    val pm = context.packageManager
    return try {
        pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException){ false }
}

