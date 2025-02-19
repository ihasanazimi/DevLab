package ir.ha.mylibrary.common.extensions

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
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import ir.ha.mylibrary.R


fun setStatusBarTransparent(activity: Activity, view: View) {
    activity.apply {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(view) { root, windowInset ->
            val inset = windowInset.getInsets(WindowInsetsCompat.Type.systemBars())
            root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = inset.left
                bottomMargin = inset.bottom
                rightMargin = inset.right
            }
            WindowInsetsCompat.CONSUMED
        }
    }
}


fun setLightStatusBar(activity: Activity,shouldBeLight :Boolean = true){
    val insetsControllerCompat = WindowInsetsControllerCompat(activity.window,activity.window.decorView)
    insetsControllerCompat.isAppearanceLightStatusBars = !shouldBeLight
}



fun Activity.setStatusBarColor(color: Int, shouldBeLight: Boolean = true) {
    if (isMarshmallowPlus()) {
        window.statusBarColor = color
        // Adjusting system UI visibility for light/dark status bar icons
        val decorView = window.decorView
        val flags = decorView.systemUiVisibility
        decorView.systemUiVisibility = if (color.isColorLight()) {
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val insetsControllerCompat =
            WindowInsetsControllerCompat(this.window, this.window.decorView)
        insetsControllerCompat.isAppearanceLightStatusBars = !shouldBeLight

    }
}


fun changeTheme(isDarkTheme: Boolean) {
    Log.i("TAG", "changeTheme function called , isDarkTheme is $isDarkTheme")
    if (isDarkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}



fun Context.copyToClipboard(text: String){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Copied Text:", text))
}


fun Context.isEnabledDarkMode() : Boolean{
    val darkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
    return darkModeFlags == Configuration.UI_MODE_NIGHT_YES //Check if the Dark Mode is On
}

fun Context.switchToDarkModeIfNeeded(){
    Log.i("", "switchToDarkModeIfNeeded - dakMode is ${this.isEnabledDarkMode()}")
    if (this.isEnabledDarkMode()) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) //Switch on the dark mode.
    }
}

fun Context.switchToLightModeIfNeeded(){
    Log.i("", "switchToLightModeIfNeeded - dakMode is ${this.isEnabledDarkMode()}")
    if (!this.isEnabledDarkMode()) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Switch off the dark mode.
    }
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

/** hide statusBar and bottom Nav */
fun hideSystemUI(window: Window) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    val controllerCompat = WindowInsetsControllerCompat(window, window.decorView)
    controllerCompat.hide(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.navigationBars())
    controllerCompat.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
}



/** show statusBar and bottom Nav */
fun showSystemUI(window: Window) {
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = true
    // And then you can set any background color to the status bar.
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.systemBars())
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

