package ir.hasanazimi.devlab.common.date_time

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatTimeAgo(inputDateString: String): String {
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val inputDate = dateFormat.parse(inputDateString)
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
                1L -> "یک سال پیش"
                2L -> "دو سال پیش"
                else -> "$years سال پیش"
            }
            months > 0 -> when (months) {
                1L -> "یک ماه پیش"
                2L -> "دو ماه پیش"
                else -> "$months ماه پیش"
            }
            weeks > 0 -> when (weeks) {
                1L -> "یک هفته پیش"
                2L -> "دو هفته پیش"
                else -> "$weeks هفته پیش"
            }
            days > 0 -> when (days) {
                1L -> "دیروز"
                2L -> "دو روز پیش"
                else -> "$days روز پیش"
            }
            hours > 0 -> when (hours) {
                1L -> "یک ساعت پیش"
                2L -> "دو ساعت پیش"
                else -> "$hours ساعت پیش"
            }
            minutes > 0 -> when (minutes) {
                1L -> "یک دقیقه پیش"
                2L -> "دو دقیقه پیش"
                else -> "$minutes دقیقه پیش"
            }
            seconds > 10 -> "$seconds ثانیه پیش"
            else -> "لحظاتی پیش"
        }
    }

    /** Sample */
    /*fun main() {
        val testDate1 = "Tue, 22 Apr 2025 12:01:19 GMT" // فرض کنید امروز 24 Apr 2025 باشد
        val testDate2 = "Tue, 23 Apr 2025 14:30:00 GMT" // دیروز
        val testDate3 = "Tue, 23 Apr 2025 15:45:00 GMT" // چند ساعت پیش

        println(formatTimeAgo(testDate1)) // خروجی: "دو روز پیش"
        println(formatTimeAgo(testDate2)) // خروجی: "دیروز"
        println(formatTimeAgo(testDate3)) // خروجی: "x ساعت پیش" (بستگی به زمان فعلی دارد)
    }*/








    fun getGregorianMonthNameInPersian(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "ژانویه"
            2 -> "فوریه"
            3 -> "مارس"
            4 -> "آوریل"
            5 -> "مه"
            6 -> "ژوئن"
            7 -> "ژوئیه"
            8 -> "اوت"
            9 -> "سپتامبر"
            10 -> "اکتبر"
            11 -> "نوامبر"
            12 -> "دسامبر"
            else -> throw IllegalArgumentException("شماره ماه باید بین ۱ تا ۱۲ باشد")
        }
    }



}