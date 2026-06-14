package ir.hasanazimi.android_compose_lab.common.helpers

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import kotlin.math.ceil

object DateHelper {

    val persianMonthNames = arrayOf(
        "\u0641\u0631\u0648\u0631\u062f\u06cc\u0646", // فروردین
        "\u0627\u0631\u062f\u06cc\u0628\u0647\u0634\u062a", // اردیبهشت
        "\u062e\u0631\u062f\u0627\u062f", // خرداد
        "\u062a\u06cc\u0631", // تیر
        "\u0645\u0631\u062f\u0627\u062f", // مرداد
        "\u0634\u0647\u0631\u06cc\u0648\u0631", // شهریور
        "\u0645\u0647\u0631", // مهر
        "\u0622\u0628\u0627\u0646", // آبان
        "\u0622\u0630\u0631", // آذر
        "\u062f\u06cc", // دی
        "\u0628\u0647\u0645\u0646", // بهمن
        "\u0627\u0633\u0641\u0646\u062f" // اسفند
    )

    val persianWeekDays = arrayOf(
        "\u0634\u0646\u0628\u0647", // شنبه
        "\u06cc\u06a9\u200c\u0634\u0646\u0628\u0647", // یک‌شنبه
        "\u062f\u0648\u0634\u0646\u0628\u0647", // دوشنبه
        "\u0633\u0647\u200c\u0634\u0646\u0628\u0647", // سه‌شنبه
        "\u0686\u0647\u0627\u0631\u0634\u0646\u0628\u0647", // چهارشنبه
        "\u067e\u0646\u062c\u200c\u0634\u0646\u0628\u0647", // پنج‌شنبه
        "\u062c\u0645\u0639\u0647" // جمعه
    )

    /**
     * بررسی سال کبیسه در تقویم جلالی با دقت بالای ریاضی
     */
    fun isPersianLeapYear(persianYear: Int): Boolean {
        return ceil(38.0 + (ceil((persianYear - 474.0), 2820.0) + 474.0) * 682.0, 2816.0) < 682.0
    }

    private fun ceil(double1: Double, double2: Double): Long {
        return (double1 - double2 * kotlin.math.floor(double1 / double2)).toLong()
    }

    fun getTimeAgo(inputDateString: String, shouldBeEnglish: Boolean): String {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val inputDate = try { dateFormat.parse(inputDateString) } catch (e: Exception) { null }
        if (inputDate == null) return if (shouldBeEnglish) "Unknown Date" else "تاریخ نامشخص"

        val now = Calendar.getInstance()
        val inputCalendar = Calendar.getInstance().apply { time = inputDate }
        val diffInMillis = now.timeInMillis - inputCalendar.timeInMillis

        return getTimeAgoFromDiff(diffInMillis, shouldBeEnglish)
    }

    fun getTimeAgo(timestampMillis: Long, shouldBeEnglish: Boolean = false): String {
        val now = System.currentTimeMillis()
        val diff = now - timestampMillis
        return getTimeAgoFromDiff(diff, shouldBeEnglish)
    }

    private fun getTimeAgoFromDiff(diffInMillis: Long, shouldBeEnglish: Boolean): String {
        if (diffInMillis < 0) return if (shouldBeEnglish) "Just now" else "همین الان"

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        return when {
            years > 0 -> if (shouldBeEnglish) { if (years == 1L) "A year ago" else "$years years ago" } else { if (years == 1L) "یک سال پیش" else "$years سال پیش" }
            months > 0 -> if (shouldBeEnglish) { if (months == 1L) "A month ago" else "$months months ago" } else { if (months == 1L) "یک ماه پیش" else "$months ماه پیش" }
            weeks > 0 -> if (shouldBeEnglish) { if (weeks == 1L) "A week ago" else "$weeks weeks ago" } else { if (weeks == 1L) "یک هفته پیش" else "$weeks هفته پیش" }
            days > 0 -> if (shouldBeEnglish) { if (days == 1L) "A day ago" else "$days days ago" } else { if (days == 1L) "یک روز پیش" else "$days روز پیش" }
            hours > 0 -> if (shouldBeEnglish) { if (hours == 1L) "An hour ago" else "$hours hours ago" } else { if (hours == 1L) "یک ساعت پیش" else "$hours ساعت پیش" }
            minutes > 0 -> if (shouldBeEnglish) { if (minutes == 1L) "A min ago" else "$minutes min ago" } else { if (minutes == 1L) "یک دقیقه پیش" else "$minutes دقیقه پیش" }
            seconds > 10 -> if (shouldBeEnglish) "$seconds seconds ago" else "$seconds ثانیه پیش"
            else -> if (shouldBeEnglish) "Moments ago" else "لحظاتی پیش"
        }
    }

    fun getFormattedDateTehran(timeMs: Long): String {
        return try {
            val sdf = SimpleDateFormat("yyyy/MM/dd (HH:mm)", Locale.ENGLISH)
            sdf.timeZone = TimeZone.getTimeZone("Asia/Tehran")
            sdf.format(Date(timeMs))
        } catch (e: Exception) {
            ""
        }
    }

    fun getGregorianMonthNameInPersian(monthNumber: Int, shouldBeEnglish: Boolean = false): String {
        return when (monthNumber) {
            1 -> if (shouldBeEnglish) "January" else "ژانویه"
            2 -> if (shouldBeEnglish) "February" else "فوریه"
            3 -> if (shouldBeEnglish) "March" else "مارس"
            4 -> if (shouldBeEnglish) "April" else "آوریل"
            5 -> if (shouldBeEnglish) "May" else "مه"
            6 -> if (shouldBeEnglish) "June" else "ژوئن"
            7 -> if (shouldBeEnglish) "July" else "ژوئیه"
            8 -> if (shouldBeEnglish) "August" else "اوت"
            9 -> if (shouldBeEnglish) "September" else "سپتامبر"
            10 -> if (shouldBeEnglish) "October" else "اکتبر"
            11 -> if (shouldBeEnglish) "November" else "نوامبر"
            12 -> if (shouldBeEnglish) "December" else "دسامبر"
            else -> throw IllegalArgumentException("شماره ماه باید بین ۱ تا ۱۲ باشد")
        }
    }

    /**
     * کلاس یکپارچه‌شده تقویم شمسی که از RoozhDateConverter برای محاسبات استفاده می‌کند
     */
    class PersianCalendar : GregorianCalendar {
        var persianYear: Int = 0
        var persianMonth: Int = 0 // 0-indexed (فروردین = 0)
        var persianDay: Int = 0
        var delimiter: String = "/"

        constructor() : super(TimeZone.getTimeZone("Asia/Tehran")) {
            calculatePersianDate()
        }

        constructor(millis: Long) : super(TimeZone.getTimeZone("Asia/Tehran")) {
            timeInMillis = millis
            calculatePersianDate()
        }

        private fun calculatePersianDate() {
            val converter = RoozhDateConverter()
            val cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"))
            cal.timeInMillis = this.timeInMillis

            converter.gregorianToPersian(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1, // Calendar.MONTH از صفر شروع می‌شود
                cal.get(Calendar.DAY_OF_MONTH)
            )

            persianYear = converter.year
            persianMonth = converter.month - 1
            persianDay = converter.day
        }

        fun setPersianDate(persianYear: Int, persianMonth: Int, persianDay: Int) {
            this.persianYear = persianYear
            this.persianMonth = persianMonth
            this.persianDay = persianDay

            val converter = RoozhDateConverter()
            converter.persianToGregorian(persianYear, persianMonth + 1, persianDay)

            val cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran"))
            cal.set(converter.year, converter.month - 1, converter.day, 0, 0, 0)
            this.timeInMillis = cal.timeInMillis
        }

        fun parse(dateString: String?) {
            val tokens = dateString?.split(delimiter.toRegex())?.dropLastWhile { it.isEmpty() }
            if (tokens?.size != 3) throw RuntimeException("wrong date: $dateString is not a Persian Date")
            setPersianDate(
                tokens[0].toInt(),
                tokens[1].toInt() - 1,
                tokens[2].toInt()
            )
        }

        val isPersianLeapYear: Boolean
            get() = DateHelper.isPersianLeapYear(persianYear)

        val persianMonthName: String
            get() = persianMonthNames.getOrElse(persianMonth) { "" }

        val persianWeekDayName: String
            get() = when (get(DAY_OF_WEEK)) {
                SATURDAY -> persianWeekDays[0]
                SUNDAY -> persianWeekDays[1]
                MONDAY -> persianWeekDays[2]
                TUESDAY -> persianWeekDays[3]
                WEDNESDAY -> persianWeekDays[4]
                THURSDAY -> persianWeekDays[5]
                else -> persianWeekDays[6]
            }

        val persianLongDate: String
            get() = "$persianWeekDayName  $persianDay  $persianMonthName  $persianYear"

        val persianShortDate: String
            get() = String.format(Locale.US, "%04d%s%02d%s%02d", persianYear, delimiter, persianMonth + 1, delimiter, persianDay)
    }

    /**
     * اکنون به درستی فرمت yyyy/MM/dd را برمی‌گرداند.
     */
    fun getCurrentPersianDate(): String {
        val persianCalendar = PersianCalendar()
        return persianCalendar.persianShortDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTimeInTehran1(): String {
        val currentTime = LocalTime.now(ZoneId.of("Asia/Tehran"))
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH)
        return currentTime.format(formatter)
    }

    fun getCurrentTimeInTehran2(): String {
        val currentTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran")).time
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        return formatter.format(currentTime)
    }

    fun timeDifference(time1: String, time2: String): String {
        try {
            if (time1.isNotEmpty() && time2.isNotEmpty()) {
                val p1 = time1.split(":").map { it.toIntOrNull() ?: 0 }
                val p2 = time2.split(":").map { it.toIntOrNull() ?: 0 }

                val h1 = p1.getOrElse(0) { 0 }
                val m1 = p1.getOrElse(1) { 0 }
                val s1 = p1.getOrElse(2) { 0 }

                val h2 = p2.getOrElse(0) { 0 }
                val m2 = p2.getOrElse(1) { 0 }
                val s2 = p2.getOrElse(2) { 0 }

                val totalSeconds1 = h1 * 3600 + m1 * 60 + s1
                val totalSeconds2 = h2 * 3600 + m2 * 60 + s2
                var differenceSeconds = Math.abs(totalSeconds1 - totalSeconds2)

                val differenceHours = differenceSeconds / 3600
                differenceSeconds %= 3600
                val differenceMinutes = differenceSeconds / 60
                differenceSeconds %= 60

                return String.format(Locale.US, "%02d:%02d:%02d", differenceHours, differenceMinutes, differenceSeconds)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getCurrentTimeIn24HourFormat(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone("Asia/Tehran")
        return dateFormat.format(Date())
    }

    fun toDateTimeOrNull(timeString: String): Date? {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            sdf.isLenient = false
            sdf.parse(timeString)
        } catch (e: Exception) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalTimeOrNull(timeString: String): LocalTime? {
        return try {
            LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH))
        } catch (e: DateTimeParseException) {
            null
        }
    }

    fun convertSecondToMilliSecond(time: Long): Long = time * 1000L

    fun convertSystemTimeMSToSecond(millisecond: Long): Long = millisecond / 1000L

    fun convertSystemTimeMSToMinute(millisecond: Long): String {
        val second = (millisecond / 1000) % 60
        val minute = (millisecond / 60000) // اصلاح باگ صفر شدن دقیقه بعد از یک ساعت
        return String.format(Locale.US, "%02d:%02d", minute, second)
    }

    fun getTimeFormat(hour: String, minute: String): String {
        return String.format(Locale.US, "%02d:%02d", hour.toIntOrNull() ?: 0, minute.toIntOrNull() ?: 0)
    }
}

/**
 * تبدیل تاریخ میلادی و شمسی با استفاده از الگوریتم Borkowski
 */
class RoozhDateConverter {
    private val mounths = arrayOf(
        "فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور",
        "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند"
    )
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0

    private var jY = 0
    private var jM = 0
    private var jD = 0
    private var gY = 0
    private var gM = 0
    private var gD = 0
    private var leap = 0
    private var march = 0

    private fun jG2JD(year: Int, month: Int, day: Int, J1G0: Int): Int {
        var jd = ((1461 * (year + 4800 + (month - 14) / 12)) / 4
                + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
                - (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day
                - 32075)
        if (J1G0 == 0) jd = jd - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752
        return jd
    }

    private fun jD2JG(JD: Int, J1G0: Int) {
        val i: Int
        var j = 4 * JD + 139361631
        if (J1G0 == 0) {
            j = j + (4 * JD + 183187720) / 146097 * 3 / 4 * 4 - 3908
        }
        i = (j % 1461) / 4 * 5 + 308
        gD = (i % 153) / 5 + 1
        gM = ((i / 153) % 12) + 1
        gY = j / 1461 - 100100 + (8 - gM) / 6
    }

    private fun jD2Jal(JDN: Int) {
        jD2JG(JDN, 0)
        jY = gY - 621
        jalCal(jY)
        val JDN1F = jG2JD(gY, 3, march, 0)
        var k = JDN - JDN1F
        if (k >= 0) {
            if (k <= 185) {
                jM = 1 + k / 31
                jD = (k % 31) + 1
                return
            } else {
                k -= 186
            }
        } else {
            jY -= 1
            k += 179
            if (leap == 1) k += 1
        }
        jM = 7 + k / 30
        jD = (k % 30) + 1
    }

    private fun jal2JD(jY: Int, jM: Int, jD: Int): Int {
        jalCal(jY)
        return (jG2JD(gY, 3, march, 1) + (jM - 1) * 31 - jM / 7 * (jM - 7) + jD - 1)
    }

    private fun jalCal(jY: Int) {
        march = 0
        leap = 0
        val breaks = intArrayOf(
            -61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210,
            1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178
        )
        gY = jY + 621
        var leapJ = -14
        var jp = breaks[0]
        var jump = 0
        for (j in 1..19) {
            val jm = breaks[j]
            jump = jm - jp
            if (jY < jm) {
                var N = jY - jp
                leapJ += N / 33 * 8 + (N % 33 + 3) / 4
                if ((jump % 33) == 4 && (jump - N) == 4) leapJ += 1
                val leapG = (gY / 4) - (gY / 100 + 1) * 3 / 4 - 150
                march = 20 + leapJ - leapG
                if ((jump - N) < 6) N = N - jump + (jump + 4) / 33 * 33
                leap = ((((N + 1) % 33) - 1) % 4)
                if (leap == -1) leap = 4
                break
            }
            leapJ += jump / 33 * 8 + (jump % 33) / 4
            jp = jm
        }
    }

    override fun toString(): String {
        return String.format(Locale.US, "%04d-%02d-%02d", this.year, this.month, this.day)
    }

    @SuppressLint("DefaultLocale")
    fun toString(strMonth: Boolean): String {
        return if (strMonth) {
            String.format(Locale.US, "%02d %s %04d", this.day, mounths[this.month - 1], this.year)
        } else {
            toString()
        }
    }

    fun gregorianToPersian(year: Int, month: Int, day: Int) {
        val jd = jG2JD(year, month, day, 0)
        jD2Jal(jd)
        this.year = jY
        this.month = jM
        this.day = jD
    }

    fun gregorianToPersian(date: Date) {
        val calender: Calendar = GregorianCalendar(TimeZone.getTimeZone("Asia/Tehran"))
        calender.time = date
        val jd = jG2JD(
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH) + 1, // Fix: تقویم ماه جاوا از صفر شروع می‌شود
            calender.get(Calendar.DAY_OF_MONTH),
            0
        )
        jD2Jal(jd)
        this.year = jY
        this.month = jM
        this.day = jD
    }

    fun persianToGregorian(year: Int, month: Int, day: Int) {
        val jd = jal2JD(year, month, day)
        jD2JG(jd, 0)
        this.year = gY
        this.month = gM
        this.day = gD
    }
}