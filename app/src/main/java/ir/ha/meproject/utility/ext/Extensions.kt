package ir.ha.meproject.utility.ext

import android.Manifest
import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.transition.Fade
import android.transition.TransitionManager
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import ir.ha.meproject.R
import ir.ha.meproject.utility.security.checkPermission
import ir.ha.meproject.utility.security.requestPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.reflect.KClass


private const val TAG = "utility_extensions"

/** Activity AND Fragment Extensions */
fun AppCompatActivity.addFragmentByAnimation(
    fragment: Fragment,
    tag: String,
    addToBackStack: Boolean,
    customAnimations: Boolean,
    containerViewId: Int,
    commitAllowingStateLoss: Boolean = false
) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    if (customAnimations) {
        fragmentTransaction.setCustomAnimations(
            R.anim.enter_fragment_anim,
            R.anim.exit_fragment_animation,
            R.anim.pop_enter_fragment_animation,
            R.anim.pop_exit_fragment_animation
        )
    }
    if (addToBackStack) fragmentTransaction.addToBackStack(tag)
    fragmentTransaction.add(containerViewId, fragment, tag)
    if (commitAllowingStateLoss) fragmentTransaction.commitAllowingStateLoss()
    else fragmentTransaction.commit()
}

fun Fragment.addFragmentByAnimation(
    fragment: Fragment,
    tag: String,
    addToBackStack: Boolean,
    customAnimations: Boolean,
    containerViewId: Int,
    commitAllowingStateLoss: Boolean = false
) {

    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    if (customAnimations) {
        fragmentTransaction.setCustomAnimations(
            R.anim.enter_fragment_anim,
            R.anim.exit_fragment_animation,
            R.anim.pop_enter_fragment_animation,
            R.anim.pop_exit_fragment_animation
        )
    }
    if (addToBackStack) { fragmentTransaction.addToBackStack(tag) }
    fragmentTransaction.add(containerViewId, fragment, tag)
    if (commitAllowingStateLoss)  fragmentTransaction.commitAllowingStateLoss()
    else fragmentTransaction.commit()
}


fun Fragment.replaceFragmentByAnimation(
    fragment: Fragment,
    tag: String,
    addToBackStack: Boolean,
    customAnimations: Boolean,
    containerViewId: Int,
    commitAllowingStateLoss: Boolean = false
) {

    val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
    if (customAnimations) {
        fragmentTransaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.pop_enter,
            R.anim.pop_exit
        )
    }
    if (addToBackStack) { fragmentTransaction.addToBackStack(tag) }
    fragmentTransaction.replace(containerViewId, fragment, tag)
    if (commitAllowingStateLoss)  fragmentTransaction.commitAllowingStateLoss()
    else fragmentTransaction.commit()
}



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

// Extension function to determine if a color is light or dark
private fun Int.isColorLight(): Boolean {
    val darkness = 1 - (0.299 * android.graphics.Color.red(this) +
            0.587 * android.graphics.Color.green(this) +
            0.114 * android.graphics.Color.blue(this)) / 255
    return darkness < 0.5
}



fun Fragment.getDrawable(drawableResID: Int): Drawable? = ContextCompat.getDrawable(requireContext(), drawableResID)?.mutate()

fun Fragment.getColor(colorResID: Int): Int = ContextCompat.getColor(requireContext(), colorResID)


fun Fragment.onBackClick(callback: (onBackPressedCallback: OnBackPressedCallback) -> Unit) {
// This callback will only be called when MyFragment is at least Started.
    activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
        callback.invoke(this)
    }
}

//fun Activity.setOnBackPressedListener(action: () -> Unit) {
//    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            action()
//        }
//    })
//}




fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun EditText.afterTextChangedEditable(afterTextChanged: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable)
        }
    })
}

fun EditText.doRequestFocus() {
    requestFocus()
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.setEditTextJustReadOnly(value: Boolean, inputType: Int = InputType.TYPE_NULL) {
    isFocusable = !value
    isFocusableInTouchMode = !value
    this.inputType = inputType
}
fun RecyclerView.scrollToTop() {
    if(canScrollVertically(-1)) smoothScrollToPosition(0)
}

fun ViewGroup.showByAnimation() {
    val transition = Fade()
    transition.duration = 500
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(this, transition)
    this.visibility = View.VISIBLE
}




fun Context.drawable(@DrawableRes drawableRes: Int) = ResourcesCompat.getDrawable(resources, drawableRes, theme)

fun getColoredDrawable(context:Context,drawableResID: Int, colorResID: Int, mode: PorterDuff.Mode): Drawable? {
    val drawable = ContextCompat.getDrawable(context, drawableResID)?.mutate()
    val colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, colorResID), mode)
    drawable?.colorFilter = colorFilter
    return drawable
}

fun getColoredDrawable(context: Context,drawable: Drawable, colorResID: Int, mode: PorterDuff.Mode): Drawable? {
    val colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, colorResID), mode)
    drawable.colorFilter = colorFilter
    return drawable
}




fun View.setPaddingLeft(value: Int) {
    setPadding(value, paddingTop, paddingRight, paddingBottom)
}
fun View.setPaddingTop(value: Int) {
    setPadding(paddingLeft, value, paddingRight, paddingBottom)
}
fun View.setPaddingRight(value: Int) {
    setPadding(paddingLeft, paddingTop, value, paddingBottom)
}
fun View.setPaddingBottom(value: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, value)
}


fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}





fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                // use this to change the link color
                textPaint.color = textPaint.linkColor
                // toggle below value to enable/disable
                // the underline shown below the clickable text
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        if(startIndexOfLink == -1) { continue }
        spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}





private val viewScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
fun View.singleClick(callback: () -> Unit) {
    this.setOnClickListener {
        viewScope.launch {
            callback.invoke()
            this@singleClick.isClickable = false
            delay(300)
            this@singleClick.isClickable = true
            this.cancel()
        }
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



fun View.dp(value: Float): Int {
    return if (value == 0f) 0 else ceil(context.resources.displayMetrics.density * value.toDouble()).toInt()
}





fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun View.showByFadeIn() {
    if (this.isVisible.not()) animate().alpha(1f).setDuration(150L).withStartAction { show() }.start()
}
fun View.hideFadeOut() {
    if (this.isVisible) animate().alpha(0f).setDuration(150L).withEndAction { hide() }.start()
}






fun Context.copyToClipboard(text: String){
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Copied Text:", text))
}




@SuppressLint("ServiceCast")
fun isMyServiceRunning(applicationContext: Context?, serviceClass: Class<*>): Boolean {
    val manager = applicationContext?.getSystemService(Context.ACCOUNT_SERVICE) as ActivityManager?
    for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}






/** ADD -> <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> */
fun isInternetConnected(context: Context?): Boolean {
    val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}





fun isAppAvailable(context: Context, appName: String): Boolean {
    val pm = context.packageManager
    return try {
        pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException){ false }
}




fun getApplicationVersion(context : Context) : Pair<String , Int>{
    var versionName = ""
    var versionCode = -1
    try {
        val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.getPackageName(), 0)
        versionName = pInfo.versionName
        versionCode = pInfo.versionCode
    } catch (e: PackageManager.NameNotFoundException) { e.printStackTrace() }
    return Pair(versionName , versionCode)
}





fun turnOnGPS(activity: Activity) {
    val manager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER).not()) {
        // turnOnGPS
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        activity.startActivity(intent)
    }
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





fun checkPermission(activity: Activity , permission: String, requestCode: Int) {
    if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
        // Requesting the permission
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    } else {
        Toast.makeText(activity, "Permission already granted", Toast.LENGTH_SHORT).show()
    }
}




fun NavController.safeNavigate(direction: NavDirections, navOptions: NavOptions? = null) {
    runCatching {
        currentDestination?.getAction(direction.actionId)?.let {
            navigate(direction.actionId, direction.arguments, navOptions)
        }
    }.onFailure {
        Log.d(TAG, "safeNavigate: ${it.message}")
    }
}

fun NavController.safeNavigateByExtra(
    direction: NavDirections,
    fragmentNavigatorExtras: FragmentNavigator.Extras? = null
) {
    currentDestination?.getAction(direction.actionId)?.let {
        runCatching {
            navigate(direction.actionId, direction.arguments, null, fragmentNavigatorExtras)
        }.onFailure {
            Log.d(TAG, "safeNavigateByExtra: ${it.message}")
        }
    }
}


fun isMobile(number: String): Boolean {
    return number.isNotEmpty() && number.matches("09\\d{9}".toRegex())
}

fun isPhoneNumber(number: String): Boolean {
    return number.matches("\\+?\\d(-|\\d)+".toRegex())
}

fun getIranStandardPhoneNumber(mobileNumber: String): String {
    var result = mobileNumber
    return try {
        result = result.replace(" ", "")
        result = result.replace("+98", "")
        if (result.length == 11) result = result.substring(1)
        result
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        mobileNumber
    }
}


fun isUrl(url: String): Boolean {
    return android.util.Patterns.WEB_URL.matcher(url).matches();
}




fun String.convertEnglishNumbersToPersian(): String {
    val englishToPersianMap = mapOf(
        '0' to '۰',
        '1' to '۱',
        '2' to '۲',
        '3' to '۳',
        '4' to '۴',
        '5' to '۵',
        '6' to '۶',
        '7' to '۷',
        '8' to '۸',
        '9' to '۹'
    )

    val result = StringBuilder(this)
    for (i in 0 until length) {
        val character = this[i]
        val persianEquivalent = englishToPersianMap[character]
        if (persianEquivalent != null) {
            result.setCharAt(i, persianEquivalent)
        }
    }
    return result.toString()
}




fun String.convertPersianNumbersToEnglish(): String {
    val persianToEnglishMap = mapOf(
        '۰' to '0',
        '۱' to '1',
        '۲' to '2',
        '۳' to '3',
        '۴' to '4',
        '۵' to '5',
        '۶' to '6',
        '۷' to '7',
        '۸' to '8',
        '۹' to '9'
    )

    val result = StringBuilder(this)
    for (i in 0 until length) {
        val character = this[i]
        val englishEquivalent = persianToEnglishMap[character]
        if (englishEquivalent != null) {
            result.setCharAt(i, englishEquivalent)
        }
    }
    return result.toString()
}



fun removeNumbers(input : String): String {
    return try {
        input.replace("0", "")
            .replace("1", "")
            .replace("2", "")
            .replace("3", "")
            .replace("4", "")
            .replace("5", "")
            .replace("6", "")
            .replace("7", "")
            .replace("8", "")
            .replace("9", "")
            .replace("۰", "")
            .replace("۱", "")
            .replace("۲", "")
            .replace("۳", "")
            .replace("۴", "")
            .replace("۵", "")
            .replace("۶", "")
            .replace("۷", "")
            .replace("۸", "")
            .replace("۹", "")

    } catch (e: Exception) {
        e.printStackTrace()
        input
    }
}



fun getTimeFormat(hour : String , minute : String) = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)



fun String.extractNumberInString() = replace("\\D+".toRegex(),"").toInt()

fun isPersian(input: String): Boolean {
    val pattern = "^[\\s\\u0621\\u0622\\u0627\\u0623\\u0628\\u067e\\u062a\\u062b\\u062c\\u0686\\u062d\\u062e\\u062f\\u0630\\u0631\\u0632\\u0698\\u0633-\\u063a\\u0641\\u0642\\u06a9\\u06af\\u0644-\\u0646\\u0648\\u0624\\u0647\\u06cc\\u0626\\u0625\\u0671\\u0643\\u0629\\u064a\\u0649]+"
    return input.matches(pattern.toRegex())
}

fun isEnglishCharacters(input : String): Boolean {
    // Regex pattern to match English characters
    val regex = "[a-zA-Z]+".toRegex()
    return input.matches(regex)
}

fun keepOnlyNumbers(text: String): String {
    val regex = "[0-9]|[۰-۹]|[٠١٢٣٤٥٦٧٨٩]"
    var result = ""
    val pattern = Pattern.compile(regex, Pattern.MULTILINE)
    val matcher = pattern.matcher(text)
    while (matcher.find()) {
        result += matcher.group(0)
    }
    return result
}


private object EnglishInputFilter : InputFilter {
    private val englishPattern = "[a-zA-Z0-9]+".toRegex()
    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        val input = source?.subSequence(start, end).toString()
        return if (input.matches(englishPattern)) null else ""
    }
}



fun EditText.setEnglishInputFilter() {
    val existingFilters = this.filters
    val newFilters = existingFilters.copyOf(existingFilters.size + 1)
    newFilters[newFilters.size - 1] = EnglishInputFilter
    this.filters = newFilters
}



private object PersianInputFilter : InputFilter {
    private val persianPattern = Pattern.compile("[\\p{IsArabic}\\s]+")
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        // Check if the source contains non-Persian characters
        val matcher = persianPattern.matcher(source)
        return if (!matcher.matches()) "" else null
    }
}




fun EditText.setPersianInputFilter() {
    val existingFilters = this.filters
    val newFilters = existingFilters.copyOf(existingFilters.size + 1)
    newFilters[newFilters.size - 1] = PersianInputFilter
    this.filters = newFilters
}





fun EditText.setNumericInputType() {
    this.inputType = InputType.TYPE_CLASS_NUMBER
}





fun String.getMoneyFormatBySeparator(): String {
    if (this.isEmpty())
        return ""
    var result = this

    try {
        val formatter: NumberFormat = DecimalFormat("#,###")
        if (result.contains(",") || result.contains('٬') || result.contains("،")) {
            result = result.replace(",", "")
            result = result.replace("٬", "")
            result = result.replace("،", "")
        }
        result = formatter.format(result.toLong())
        result = result.convertEnglishNumbersToPersian()
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    return result
}






fun Double.toStandardDecimal(): String {
    val floatString = this.toString()
    val decimalString: String = floatString.substring(floatString.indexOf('.') + 1, floatString.length)

    return when (decimalString.toInt() == 0) {
        true -> this.toInt().toString()
        false -> "%.3f".format(this)
    }
}









fun convertDpToPx(dp : Float, context : Context?) : Float {
    return if (context != null){
        val resource = context.resources
        val metrics = resource.displayMetrics
        dp / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }else{
        val metrics = Resources.getSystem().displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}





fun convertDpiToPixel(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)


fun addTurnScreenOnAlwaysFlag(window : Window){
    window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
}


fun clearTurnScreenOnAlwaysFlag(window : Window){
    window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
}





fun convertSecondToMilliSecond(time: Long): Long {
    return time * 1000L
}

fun convertSystemTimeMSToSecond(time: Long): Long {
    return time / 1000L
}

fun convertSystemTimeMSToMinute(millisecond: Long): String? {
    val second = (millisecond / 1000) % 60
    val minute = (millisecond / (1000 * 60)) % 60
    return String.format(Locale.US,"%02d:%02d",minute,second)
}





fun KClass<*>.simpleClassName():String{
    // returned -> ClassName -> className
    return this.toString().split(".").last().convertPascalCaseToCamelCase().also {
        Log.i(TAG, "simpleClassName: $it")
    }
}

fun String.convertPascalCaseToCamelCase():String{
    return this.replaceFirst(this.substring(0,1),this.substring(0,1).toLowerCase())
}




fun keyboardListener(
    activity: Activity,
    view: View,
    lifecycleOwner: Lifecycle,
    callBack: (keyboardIsOpen: Boolean) -> Unit
) {
    var isKeyboardOpenLastState = false
    var isKeyboardOpen = false
    Log.i(TAG, "handleKeyboardAndAnimation: ")
    view.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
        Log.i(
            TAG,
            "onGlobalLayout: ======================================================================================================"
        )
        val layoutRect = Rect()
        var translationYAnimator: ObjectAnimator? = null
        //r will be populated with the coordinates of your view that area still visible.
        view.getWindowVisibleDisplayFrame(layoutRect)
        val statusBarHeight = layoutRect.top
        Log.i(TAG, "onGlobalLayout: statusBarHeight => $statusBarHeight")
//        isKeyboardOpenLastState = isKeyboardOpen

        ///////////////////////////// get focus view ////////////////////////////////////////
        val focusedView: View?
        val focusedViewRect = Rect()
        var focusedViewHeight = 0
        var focusedViewY = 0
        var heightDiff = 0
        heightDiff = view.rootView.height - (layoutRect.bottom - layoutRect.top)
        focusedView = activity.currentFocus
        if (focusedView != null) {
            Log.i(TAG, "onGlobalLayout: focusedView is not null => " + focusedView.id)
            focusedView.getWindowVisibleDisplayFrame(focusedViewRect)
            focusedViewHeight = focusedView.height
            Log.i(TAG, "onGlobalLayout: focusedViewHeight => $focusedViewHeight")
            val locationWin = IntArray(2)
            focusedView.getLocationInWindow(locationWin)
            focusedViewY = locationWin[1]
            Log.i(TAG, "onGlobalLayout: focusedViewY => $focusedViewY")
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
//        if (isKeyboardOpenLastState==isKeyboardOpen)
//            return@OnGlobalLayoutListener

        if (heightDiff > 0.25 * view.rootView.height) { // if more than 25% of the screen, its probably a keyboard...
            Log.i(TAG, "onGlobalLayout: keyboard opened")
            isKeyboardOpen = true
        } else {
            Log.i(TAG, "onGlobalLayout: keyboard closed")
            isKeyboardOpen = false
        }

        if (isKeyboardOpen == isKeyboardOpenLastState)
            return@OnGlobalLayoutListener


        if (lifecycleOwner.currentState.isAtLeast(Lifecycle.State.STARTED))
            callBack.invoke(isKeyboardOpen)

        isKeyboardOpenLastState = isKeyboardOpen
    })
}






fun AppBarLayout.offsetChangeListener(
    callBack: (
        offset: Int,
        percent: Float,
        anchorHeight: Int,
        maxScrollRange: Float
    ) -> Unit
) {
    Log.i("offsetChangeListener", "offsetChangeListener: ")
    var offset = 0
    var correctHeight = 0f
    var percent = 0f
    var lastOffset = -1
    addOnOffsetChangedListener { appBarLayout, verticalOffset ->
        if (lastOffset == verticalOffset)
            return@addOnOffsetChangedListener

        lastOffset = verticalOffset

        val maxScroll = appBarLayout.totalScrollRange.toFloat()
        offset = abs(verticalOffset)
        percent =
            if (offset != 0)
                (offset / maxScroll)
            else
                0f

        // used for anchor view
        correctHeight = abs((percent * height) - height)


        Log.i("offsetChangeListener", "offset: $offset percent: $percent")
        callBack.invoke(
            offset,
            percent,
            correctHeight.toInt(),
            maxScroll
        )
    }
}





inline fun <T : Any , R> T?.withNotNull(block : (T) -> R) : R? {
    return this?.let(block)
    // means -> if(B != null) { run this code block }
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


fun Context.isEnabledDarkMode() : Boolean{
    val darkModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK// Retrieve the Mode of the App.
    return darkModeFlags == Configuration.UI_MODE_NIGHT_YES //Check if the Dark Mode is On
}

fun Context.switchToDarkModeIfNeeded(){
    Log.i(TAG, "switchToDarkModeIfNeeded - dakMode is ${this.isEnabledDarkMode()}")
    if (this.isEnabledDarkMode()) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) //Switch on the dark mode.
    }
}

fun Context.switchToLightModeIfNeeded(){
    Log.i(TAG, "switchToLightModeIfNeeded - dakMode is ${this.isEnabledDarkMode()}")
    if (!this.isEnabledDarkMode()) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Switch off the dark mode.
    }
}






fun changeTheme(isDarkTheme: Boolean) {
    Log.i(TAG, "changeTheme function called , isDarkTheme is $isDarkTheme")
    if (isDarkTheme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}





open class AnimatorListenerImpl : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationEnd(animation: Animator) {}
    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}
}







fun doEnable(enable: Boolean, vararg views: View){
    for (i in views){
        i.isEnabled = enable
        i.isClickable = enable
        i.isFocusable = enable
    }
}




@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
fun isMarshmallowPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
fun isNougatPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N_MR1)
fun isNougatMR1Plus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
fun isOreoPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O_MR1)
fun isOreoMr1Plus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
fun isPiePlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
fun isQPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
fun isRPlusPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S_V2)
fun isSV2Plus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun isTIRAMISUPlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun isUPSIDE_DOWN_CAKE_Plus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE




fun String.isValidPassportNumber(): Boolean {
    val passportRegex = "^[A-Z0-9]{6,20}$".toRegex()
    return passportRegex.matches(this)
}



fun String.isValidNationalCode(): Boolean {
    val nationalCode = this.trim()

    // Check if the national code consists of only digits and has a valid length
            if (!nationalCode.matches(Regex("\\d{10}"))) {
                return false
            }
    // Convert the national code string to an array of integers
    val digits = nationalCode.map { it.toString().toInt() }

    // Check if all digits are equal (not allowed in national codes)
    if (digits.all { it == digits[0] }) {
        return false
    }

    // Calculate the verification digit
    val verificationDigit = digits[9]
    val calculatedVerificationDigit = (0..8).sumOf { (digits[it] * (10 - it)) } % 11
    val expectedVerificationDigit =
        if (calculatedVerificationDigit < 2) 0 else 11 - calculatedVerificationDigit

    return verificationDigit == expectedVerificationDigit
}





inline fun <reified T:Any> String.castFromJson(): T? {
    return Gson().fromJson(this, T::class.java)
}





fun ImageView.setIconTint(colorID : Int){
    this.setColorFilter(ContextCompat.getColor(this.context, colorID))
}



@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalTimeOrNull(): LocalTime? {
    return try {
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: DateTimeParseException) {
        null
    }
}



fun String.toDateTimeOrNull(): Date? {
    return try {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        sdf.isLenient = false // This will strict the parsing
        sdf.parse(this)
    } catch (e: Exception) {
        null
    }
}




fun parseHexColorResource(context: Context, hexColor: String): Int {
    // Parse hex color to get the color integer
    val colorString = if (hexColor.startsWith("#")) hexColor.substring(1) else hexColor
    // Convert the color string to a color integer
    val colorInt = try { Color.parseColor("#$colorString")
    } catch (e: IllegalArgumentException) {
        // Handle invalid color format
        ContextCompat.getColor(context, android.R.color.transparent)
    }
    // Return a color resource using the color integer
    return context.resources.getColor(colorInt, context.theme)
}




/** add -> implementation("com.google.android.gms:play-services-location:21.2.0") */
fun Activity.getCurrentLocation(callback: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000 // Update interval in milliseconds
        fastestInterval = 5000 // Fastest update interval in milliseconds
    }
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(lr: LocationResult) {
            lr ?: return
            for (location in lr.locations) {
                callback(location)
                return
            }
        }
    }

    if (checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }
    /** add permission ->     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> */
    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
}





fun getCurrentPersianDate(): String {
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"), Locale("fa", "IR"))
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Months are 0-based
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    return "$year/$month/$day" // You can format the date as needed
}


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTimeInTehran1(): String {
    val currentTime = LocalTime.now(ZoneId.of("Asia/Tehran"))
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return currentTime.format(formatter)
}

fun getCurrentTimeInTehran2(): String {
    val currentTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran")).time
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return formatter.format(currentTime)
}


fun showToast(ctx : Context , message : String){
    Toast.makeText(ctx,message.trim() , Toast.LENGTH_SHORT).show()
}



fun timeDifference(time1: String, time2: String): String {
    if (time1.isNotEmpty() && time2.isNotEmpty()) {
        // Splitting the time strings to get hours, minutes, and seconds separately
        val (hours1, minutes1, seconds1) = time1.split(":").map { it.toInt() }
        val (hours2, minutes2, seconds2) = time2.split(":").map { it.toInt() }

        // Converting both times to seconds
        val totalSeconds1 = hours1 * 3600 + minutes1 * 60 + seconds1
        val totalSeconds2 = hours2 * 3600 + minutes2 * 60 + seconds2

        // Calculating the absolute difference in seconds
        var differenceSeconds = Math.abs(totalSeconds1 - totalSeconds2)

        // Calculating the hours, minutes, and seconds of the difference
        val differenceHours = differenceSeconds / 3600
        differenceSeconds %= 3600
        val differenceMinutes = differenceSeconds / 60
        differenceSeconds %= 60

        // Formatting the result
        return String.format("%02d:%02d:%02d", differenceHours, differenceMinutes, differenceSeconds)
    } else {
        return ""
    }

    /** Example usage:
    fun main() {
    val time1 = "12:30:15"
    val time2 = "10:45:30"
    println("Time difference: ${timeDifference(time1, time2)}")
    // Output will be: 01:44:45
    }
     */

}





fun getCurrentTimeIn24HourFormat(): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(Date())
}



fun smoothScrollToThisView(scrollView: ScrollView,targetView : View){
    runCatching {
        Handler(Looper.myLooper()!!).postDelayed({
            scrollView.post {
                scrollView.smoothScrollTo(
                    0,
                    targetView.top - scrollView.paddingTop
                )
            }
        },500)
    }.onFailure {
        Log.e(TAG, "smoothScrollToThisView: ${it.message}", )
    }
}




