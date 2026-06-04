package ir.hasanazimi.androidlab.common.helpers

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
import kotlin.math.floor
import kotlin.math.roundToLong

object DateHelper {
    const val PERSIAN_EPOCH = 1948321
    val persianMonthNames = arrayOf(
        "\u0641\u0631\u0648\u0631\u062f\u06cc\u0646",
        "\u0627\u0631\u062f\u06cc\u0628\u0647\u0634\u062a",
        "\u062e\u0631\u062f\u0627\u062f",
        "\u062a\u06cc\u0631",
        "\u0645\u0631\u062f\u0627\u062f",
        "\u0634\u0647\u0631\u06cc\u0648\u0631",
        "\u0645\u0647\u0631",
        "\u0622\u0628\u0627\u0646",
        "\u0622\u0630\u0631",
        "\u062f\u06cc",
        "\u0628\u0647\u0645\u0646",
        "\u0627\u0633\u0641\u0646\u062f"
    )
    val persianWeekDays = arrayOf(
        "\u0634\u0646\u0628\u0647",
        "\u06cc\u06a9\u200c\u0634\u0646\u0628\u0647",
        "\u062f\u0648\u0634\u0646\u0628\u0647",
        "\u0633\u0647\u200c\u0634\u0646\u0628\u0647",
        "\u0686\u0647\u0627\u0631\u0634\u0646\u0628\u0647",
        "\u067e\u0646\u062c\u200c\u0634\u0646\u0628\u0647",
        "\u062c\u0645\u0639\u0647"
    )

    /**
     * Checks if Persian year is leap year.
     * Input: Persian year (int). Output: Boolean.
     */
    fun isPersianLeapYear(persianYear: Int): Boolean {
        return ceil(38.0 + (ceil((persianYear - 474.0).toDouble(), 2820.0) + 474.0) * 682.0, 2816.0) < 682.0
    }

    /**
     * Converts Persian date to Julian day number.
     * Input: Persian year, month, day. Output: Julian day (Long).
     */
    fun persianToJulian(year: Long, month: Int, day: Int): Long {
        return (365L * (ceil((year - 474.0).toDouble(), 2820.0) + 474.0 - 1L) +
                (floor(682L * (ceil((year - 474.0).toDouble(), 2820.0) + 474.0 - 110.0) / 2816.0).toLong()) +
                (PERSIAN_EPOCH - 1L) +
                (1029983L * floor((year - 474.0) / 2820.0).toLong()) +
                (if (month < 7) 31 * month else 30 * month + 6) +
                day).roundToLong()
    }

    private fun ceil(double1: Double, double2: Double): Long {
        return (double1 - double2 * floor(double1 / double2)).toLong()
    }

    /**
     * Formats time difference into human-readable string.
     * Input: ISO date string. Output: "X ago" string.
     */
    fun getTimeAgo(inputDateString: String, shouldBeEnglish: Boolean): String {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val inputDate = dateFormat.parse(inputDateString)
        if (inputDate == null) return if (shouldBeEnglish) "Unknown Date" else "تاریخ نامشخص"

        val now = Calendar.getInstance()
        val inputCalendar = Calendar.getInstance().apply { time = inputDate }
        val diffInMillis = now.timeInMillis - inputCalendar.timeInMillis

        val seconds = diffInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365

        return when {
            years > 0 -> when (years) {
                1L -> if (shouldBeEnglish) "A year ago" else "یک سال پیش"
                2L -> if (shouldBeEnglish) "2 years ago" else "دو سال پیش"
                else -> if (shouldBeEnglish) "$years years ago" else "$years سال پیش"
            }
            months > 0 -> when (months) {
                1L -> if (shouldBeEnglish) "A month ago" else "یک ماه پیش"
                2L -> if (shouldBeEnglish) "2 months ago" else "دو ماه پیش"
                else -> if (shouldBeEnglish) "$months months ago" else "$months ماه پیش"
            }
            weeks > 0 -> when (weeks) {
                1L -> if (shouldBeEnglish) "A week ago" else "یک هفته پیش"
                2L -> if (shouldBeEnglish) "2 weeks ago" else "دو هفته پیش"
                else -> if (shouldBeEnglish) "$weeks weeks ago" else "$weeks هفته پیش"
            }
            days > 0 -> when (days) {
                1L -> if (shouldBeEnglish) "Yesterday" else "دیروز"
                2L -> if (shouldBeEnglish) "2 days ago" else "دو روز پیش"
                else -> if (shouldBeEnglish) "$days days ago" else "$days روز پیش"
            }
            hours > 0 -> when (hours) {
                1L -> if (shouldBeEnglish) "An hour ago" else "یک ساعت پیش"
                2L -> if (shouldBeEnglish) "2 hours ago" else "دو ساعت پیش"
                else -> if (shouldBeEnglish) "$hours hours ago" else "$hours ساعت پیش"
            }
            minutes > 0 -> when (minutes) {
                1L -> if (shouldBeEnglish) "A minute ago" else "یک دقیقه پیش"
                2L -> if (shouldBeEnglish) "2 minutes ago" else "دو دقیقه پیش"
                else -> if (shouldBeEnglish) "$minutes minutes ago" else "$minutes دقیقه پیش"
            }
            seconds > 10 -> if (shouldBeEnglish) "$seconds seconds ago" else "$seconds ثانیه پیش"
            else -> if (shouldBeEnglish) "Moments ago" else "لحظاتی پیش"
        }
    }

    /**
     * Formats time difference from timestamp.
     * Input: Timestamp (Long). Output: "X ago" string.
     */
    fun getTimeAgo(timestampMillis: Long, shouldBeEnglish: Boolean = false): String {
        val now = System.currentTimeMillis()
        val diff = now - timestampMillis
        if (diff < 0) return if (shouldBeEnglish) "Just now" else "همین الان"

        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val weeks = days / 7

        return when {
            seconds < 60 -> if (shouldBeEnglish) "now" else "لحظاتی پیش"
            minutes == 1L -> if (shouldBeEnglish) "A min ago" else "یک دقیقه پیش"
            minutes < 60 -> if (shouldBeEnglish) "$minutes min ago" else "$minutes دقیقه پیش"
            hours == 1L -> if (shouldBeEnglish) "An hour ago" else "یک ساعت پیش"
            hours < 24 -> if (shouldBeEnglish) "$hours hours ago" else "$hours ساعت پیش"
            days == 1L -> if (shouldBeEnglish) "A day ago" else "یک روز پیش"
            days == 2L -> if (shouldBeEnglish) "2 days ago" else "دو روز پیش"
            days < 7 -> if (shouldBeEnglish) "$days days ago" else "$days روز پیش"
            weeks == 1L -> if (shouldBeEnglish) "A week ago" else "یک هفته پیش"
            weeks == 2L -> if (shouldBeEnglish) "2 weeks ago" else "دو هفته پیش"
            else -> if (shouldBeEnglish) "$weeks weeks ago" else "$weeks هفته پیش"
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

    /**
     * Gets Persian month name from Gregorian month number.
     * Input: Gregorian month (1-12). Output: Persian month name.
     */
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

    class PersianCalendar : GregorianCalendar {
        var persianYear: Int = 0
        var persianMonth: Int = 0
        var persianDay: Int = 0
        var delimiter: String = "/"

        constructor() : super(TimeZone.getTimeZone("GMT")) {
            calculatePersianDate()
        }

        constructor(millis: Long) : super(TimeZone.getTimeZone("GMT")) {
            timeInMillis = millis
            calculatePersianDate()
        }

        private fun calculatePersianDate() {
            val julianDate = (timeInMillis - PERSIAN_EPOCH * 86400000L) / 86400000L
            val persianDate = julianToPersian(julianDate)
            persianYear = (persianDate shr 16).toInt()
            persianMonth = ((persianDate and 0xff00L).toInt() shr 8) - 1
            persianDay = (persianDate and 0xffL).toInt()
        }

        private fun julianToPersian(julianDate: Long): Long {
            val persianEpochInJulian = julianDate - persianToJulian(475L, 0, 1)
            val cyear = ceil(persianEpochInJulian.toDouble(), 1029983.0).toLong()
            val ycycle = if (cyear != 1029982L) {
                floor((2816.0 * cyear.toDouble() + 1031337.0) / 1028522.0).toLong()
            } else {
                2820L
            }
            val year = 474L + 2820L * floor(persianEpochInJulian / 1029983.0).toLong() + ycycle
            val aux = (1L + julianDate) - persianToJulian(year, 0, 1)
            val month = if (aux > 186L) {
                (ceil((aux - 6L).toDouble() / 30.0) - 1).toInt()
            } else {
                (ceil(aux.toDouble() / 31.0) - 1).toInt()
            }
            val day = (julianDate - (persianToJulian(year, month, 1) - 1L)).toInt()
            return (year shl 16) or (month shl 8).toLong() or day.toLong()
        }

        fun setPersianDate(persianYear: Int, persianMonth: Int, persianDay: Int) {
            this.persianYear = persianYear
            this.persianMonth = persianMonth
            this.persianDay = persianDay
            timeInMillis = convertToMilis(persianToJulian(persianYear.toLong(), persianMonth - 1, persianDay))
        }

        private fun convertToMilis(julianDate: Long): Long {
            return PERSIAN_EPOCH * 86400000L + julianDate * 86400000L
        }

        fun parse(dateString: String?) {
            val tokens = dateString?.split(delimiter.toRegex())?.dropLastWhile { it.isEmpty() }
            if (tokens?.size != 3) throw RuntimeException("wrong date: $dateString is not a Persian Date")
            setPersianDate(
                tokens[0].toInt(),
                tokens[1].toInt(),
                tokens[2].toInt()
            )
        }

        val isPersianLeapYear: Boolean
            get() = isPersianLeapYear(persianYear)

        val persianMonthName: String?
            get() = persianMonthNames[persianMonth]

        val persianWeekDayName: String?
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
            get() = "${formatToMilitary(persianYear)}/$delimiter${formatToMilitary(persianMonth + 1)}/$delimiter${formatToMilitary(persianDay)}"

        private fun formatToMilitary(i: Int): String {
            return if (i < 10) "0$i" else i.toString()
        }
    }

    /**
     * Gets current Persian date.
     * Input: None. Output: Persian date string (YYYY/MM/DD).
     */
    fun getCurrentPersianDate(): String {
        val persianCalendar = PersianCalendar()
        return "${persianCalendar.persianYear}/${persianCalendar.persianMonth + 1}/${persianCalendar.persianDay}"
    }

    /**
     * Gets current time in Tehran (ISO format).
     * Input: None. Output: Time string (HH:mm:ss).
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTimeInTehran1(): String {
        val currentTime = LocalTime.now(ZoneId.of("Asia/Tehran"))
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return currentTime.format(formatter)
    }

    /**
     * Gets current time in Tehran (using Calendar).
     * Input: None. Output: Time string (HH:mm:ss).
     */
    fun getCurrentTimeInTehran2(): String {
        val currentTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tehran")).time
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return formatter.format(currentTime)
    }

    /**
     * Calculates time difference between two times.
     * Input: Two time strings (HH:mm:ss). Output: Difference (HH:mm:ss).
     */
    fun timeDifference(time1: String, time2: String): String {
        if (time1.isNotEmpty() && time2.isNotEmpty()) {
            val (hours1, minutes1, seconds1) = time1.split(":").map { it.toInt() }
            val (hours2, minutes2, seconds2) = time2.split(":").map { it.toInt() }
            val totalSeconds1 = hours1 * 3600 + minutes1 * 60 + seconds1
            val totalSeconds2 = hours2 * 3600 + minutes2 * 60 + seconds2
            var differenceSeconds = Math.abs(totalSeconds1 - totalSeconds2)
            val differenceHours = differenceSeconds / 3600
            differenceSeconds %= 3600
            val differenceMinutes = differenceSeconds / 60
            differenceSeconds %= 60
            return String.format("%02d:%02d:%02d", differenceHours, differenceMinutes, differenceSeconds)
        } else {
            return ""
        }
    }

    /**
     * Gets current time in 24-hour format.
     * Input: None. Output: Time string (HH:mm).
     */
    fun getCurrentTimeIn24HourFormat(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(Date())
    }

    /**
     * Converts time string to Date object.
     * Input: Time string (HH:mm). Output: Date or null.
     */
    fun toDateTimeOrNull(timeString: String): Date? {
        return try {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(timeString)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Converts time string to LocalTime object.
     * Input: Time string (HH:mm). Output: LocalTime or null.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalTimeOrNull(timeString: String): LocalTime? {
        return try {
            LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: DateTimeParseException) {
            null
        }
    }

    /**
     * Converts seconds to milliseconds.
     * Input: Seconds (Long). Output: Milliseconds (Long).
     */
    fun convertSecondToMilliSecond(time: Long): Long {
        return time * 1000L
    }

    /**
     * Converts milliseconds to seconds.
     * Input: Milliseconds (Long). Output: Seconds (Long).
     */
    fun convertSystemTimeMSToSecond(millisecond: Long): Long {
        return millisecond / 1000L
    }

    /**
     * Converts milliseconds to minutes:seconds format.
     * Input: Milliseconds (Long). Output: "MM:SS" string.
     */
    fun convertSystemTimeMSToMinute(millisecond: Long): String? {
        val second = (millisecond / 1000) % 60
        val minute = (millisecond / (1000 * 60)) % 60
        return String.format(Locale.US, "%02d:%02d", minute, second)
    }

    /**
     * Formats hour and minute into "HH:MM" string.
     * Input: Hour and minute strings. Output: Formatted time string.
     */
    fun getTimeFormat(hour: String, minute: String): String {
        return String.format(Locale.getDefault(), "%02d:%02d", hour.toInt(), minute.toInt())
    }
}

/**
 * Converts between Gregorian and Jalali (Persian) dates.
 * Uses Borkowski's algorithm for accurate conversions.
 */
class RoozhDateConverter {
    private val mounths = arrayOf<String?>(
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر",
        "مرداد",
        "شهریور",
        "مهر",
        "آبان",
        "آذر",
        "دی",
        "بهمن",
        "اسفند"
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
        var j: Int
        j = 4 * JD + 139361631
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
                k = k - 186
            }
        } else {
            jY = jY - 1
            k = k + 179
            if (leap == 1) k = k + 1
        }
        jM = 7 + k / 30
        jD = (k % 30) + 1
    }

    private fun jal2JD(jY: Int, jM: Int, jD: Int): Int {
        jalCal(jY)
        val jd = (jG2JD(gY, 3, march, 1) + (jM - 1) * 31 - jM / 7 * (jM - 7)
                + jD - 1)
        return jd
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
                leapJ = leapJ + N / 33 * 8 + (N % 33 + 3) / 4
                if ((jump % 33) == 4 && (jump - N) == 4) leapJ = leapJ + 1
                val leapG = (gY / 4) - (gY / 100 + 1) * 3 / 4 - 150
                march = 20 + leapJ - leapG
                if ((jump - N) < 6) N = N - jump + (jump + 4) / 33 * 33
                leap = ((((N + 1) % 33) - 1) % 4)
                if (leap == -1) leap = 4
                break
            }
            leapJ = leapJ + jump / 33 * 8 + (jump % 33) / 4
            jp = jm
        }
    }

    override fun toString(): String {
        return String.format("%04d-%02d-%02d", this.year, this.month, this.day)
    }

    @SuppressLint("DefaultLocale")
    fun toString(strMonth: Boolean): String {
        if (strMonth) return String.format(
            "%02d %s %04d",
            this.day, mounths[this.month - 1],
            this.year
        )
        else return toString()
    }

    /**
     * Converts Gregorian date to Persian date.
     * Input: Gregorian year, month, day. Output: Sets Persian date.
     */
    fun gregorianToPersian(year: Int, month: Int, day: Int) {
        val jd = jG2JD(year, month, day, 0)
        jD2Jal(jd)
        this.year = jY
        this.month = jM
        this.day = jD
    }

    /**
     * Converts Gregorian date to Persian date.
     * Input: Date object. Output: Sets Persian date.
     */
    fun gregorianToPersian(date: Date) {
        val calender: Calendar = GregorianCalendar()
        calender.setTime(date)
        val jd = jG2JD(
            calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(
                Calendar.DAY_OF_MONTH
            ), 0
        )
        jD2Jal(jd)
        this.year = jY
        this.month = jM
        this.day = jD
    }

    /**
     * Converts Persian date to Gregorian date.
     * Input: Persian year, month, day. Output: Sets Gregorian date.
     */
    fun persianToGregorian(year: Int, month: Int, day: Int) {
        val jd = jal2JD(year, month, day)
        jD2JG(jd, 0)
        this.year = gY
        this.month = gM
        this.day = gD
    }
}