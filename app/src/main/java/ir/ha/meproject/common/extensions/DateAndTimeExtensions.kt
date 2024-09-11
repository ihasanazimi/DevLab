package ir.ha.meproject.common.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

fun String.toDateTimeOrNull(): Date? {
    return try {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        sdf.isLenient = false // This will strict the parsing
        sdf.parse(this)
    } catch (e: Exception) {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalTimeOrNull(): LocalTime? {
    return try {
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: DateTimeParseException) {
        null
    }
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

fun getTimeFormat(hour : String , minute : String) = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
