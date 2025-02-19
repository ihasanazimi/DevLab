package ir.ha.mylibrary.common.extensions

import android.animation.Animator
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
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
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import ir.ha.mylibrary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.regex.Pattern
import kotlin.reflect.KClass


private const val TAG = "EXTENSIONS_TAG"

private val viewScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

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







fun Fragment.onBackClick(callback: (onBackPressedCallback: OnBackPressedCallback) -> Unit) {
    activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
        callback.invoke(this)
    }
}



fun ImageView.setIconTint(colorID : Int){
    this.setColorFilter(ContextCompat.getColor(this.context, colorID))
}


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




fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }



fun View.showByFadeIn() {
    viewScope.launch {
        if (this@showByFadeIn.isVisible.not()) {
            animate().alpha(1f).setDuration(150L).withStartAction { show() }.start()
            delay(150L)
            this.cancel()
        }
    }
}
fun View.hideFadeOut() {
    viewScope.launch {
        if (this@hideFadeOut.isVisible) {
            animate().alpha(0f).setDuration(150L).withEndAction { hide() }.start()
            delay(150L)
            this.cancel()
        }
    }
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



fun removeAnyNumbers(input : String): String {
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




fun String.getAmountFormatBySeparator(): String {
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





private fun String.convertPascalCaseToCamelCase():String{
    return this.replaceFirst(this.substring(0,1),this.substring(0,1).toLowerCase())
}
fun KClass<*>.simpleClassName():String{
    // returned -> ClassName -> className
    return this.toString().split(".").last().convertPascalCaseToCamelCase().also {
        Log.i(TAG, "simpleClassName: $it")
    }
}



inline fun <T : Any , R> T?.withNotNull(block : (T) -> R) : R? {
    return this?.let(block)
    // means -> if(B != null) { run this code block }
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


fun showToast(ctx : Context , message : String){
    Toast.makeText(ctx,message, Toast.LENGTH_SHORT).show()
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



fun isMyServiceRunning(applicationContext: Context?, serviceClass: Class<*>): Boolean {
    val manager = applicationContext?.getSystemService(Context.ACCOUNT_SERVICE) as ActivityManager?
    for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}



inline fun <reified T:Any> String.castFromJson(): T? {
    return Gson().fromJson(this, T::class.java)
}



fun isValidPostalCode(postalCode: String): Boolean {
    // Rule 1: The number of postal code characters should be 10 digits.
    if (postalCode.length != 10) {
        return false
    }

    // Rule 2: In the first five digits of the postal code, 0 and 2 are not valid.
    val firstFive = postalCode.substring(0, 5)
    if (firstFive.contains('0') || firstFive.contains('2')) {
        return false
    }

    // Rule 3: The fifth digit of the postal code cannot be 5.
    if (postalCode[4] == '5') {
        return false
    }

    // Rule 4: The sixth digit of the postal code cannot be 0.
    if (postalCode[5] == '0') {
        return false
    }

    // Rule 5: The last four digits of the postal code must be valid numbers (from 0 to 9).
    val lastFour = postalCode.substring(6)
    if (!lastFour.all { it.isDigit() }) {
        return false
    }

    // Rule 6: Inserting "0000" in the last four digits of the postal code is invalid.
    if (lastFour == "0000") {
        return false
    }

    // Rule 7: All repeated numbers at the end of the postal code are invalid, such as "1111111111".
    if (postalCode.all { it == postalCode[0] }) {
        return false
    }

    return true
}
