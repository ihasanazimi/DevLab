package ir.hasanazimi.devlab.common.date_time

import java.util.Calendar
import java.util.GregorianCalendar

/**
 * Title: Calender Conversion class
 * Description: Convert Iranian (Jalali), Julian, and Gregorian dates to
 * each other
 * Public Methods Summary:
 * -----------------------
 * JavaSource_Calendar();
 * JavaSource_Calendar(int year, int month, int day);
 * int getIranianYear();
 * int getIranianMonth();
 * int getIranianDay();
 * int getGregorianYear();
 * int getGregorianMonth();
 * int getGregorianDay();
 * int getJulianYear();
 * int getJulianMonth();
 * int getJulianDay();
 * String getIranianDate();
 * String getGregorianDate();
 * String getJulianDate();
 * String getWeekDayStr();
 * String toString();
 * int getDayOfWeek();
 * void nextDay();
 * void nextDay(int days);
 * void previousDay();
 * void previousDay)int days);
 * void setIranianDate(int year, int month, int day);
 * void setGregorianDate(int year, int month, int day);
 * void setJulianDate(int year, int month, int day);
 */
class PersianDateConversion {
    /**
     * JavaSource_Calendar:
     * The default constructor uses the current Gregorian date to initialize the
     * other private memebers of the class (Iranian and Julian dates).
     */
    constructor() {
        val calendar: Calendar = GregorianCalendar()
        setGregorianDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    /**
     * JavaSource_Calendar:
     * This constructor receives a Gregorian date and initializes the other private
     * members of the class accordingly.
     * @param year int
     * @param month int
     * @param day int
     */
    constructor(year: Int, month: Int, day: Int) {
        setGregorianDate(year, month, day)
    }

    val iranianDate: String
        /**
         * getIranianDate:
         * Returns a string version of Iranian date
         * @return String
         */
        get() = (iranianYear.toString() + "/" + this.iranianMonth + "/" + this.iranianDay)

    val gregorianDate: String
        /**
         * getGregorianDate:
         * Returns a string version of Gregorian date
         * @return String
         */
        get() = (gregorianYear.toString() + "/" + this.gregorianMonth + "/" + this.gregorianDay)

    val julianDate: String
        /**
         * getJulianDate:
         * Returns a string version of Julian date
         * @return String
         */
        get() = (julianYear.toString() + "/" + this.julianMonth + "/" + this.julianDay)

    val weekDayStr: String
        /**
         * getWeekDayStr:
         * Returns the week day name.
         * @return String
         */
        get() {
            val weekDayStr: Array<String>? = arrayOf<String>(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
            )
            return (weekDayStr!![this.dayOfWeek])
        }

    /**
     * toString:
     * Overrides the default toString() method to return all dates.
     * @return String
     */
    override fun toString(): String {
        return (this.weekDayStr +
                ", Gregorian:[" + this.gregorianDate +
                "], Julian:[" + this.julianDate +
                "], Iranian:[" + this.iranianDate + "]")
    }

    val dayOfWeek: Int
        /**
         * getDayOfWeek:
         * Returns the week day number. Monday=0..Sunday=6;
         * @return int
         */
        get() = (JDN % 7)

    /**
     * nextDay:
     * Go to next julian day number (JDN) and adjusts the other dates.
     */
    fun nextDay() {
        JDN++
        JDNToIranian()
        JDNToJulian()
        JDNToGregorian()
    }

    /**
     * nextDay:
     * Overload the nextDay() method to accept the number of days to go ahead and
     * adjusts the other dates accordingly.
     * @param days int
     */
    fun nextDay(days: Int) {
        JDN += days
        JDNToIranian()
        JDNToJulian()
        JDNToGregorian()
    }

    /**
     * previousDay:
     * Go to previous julian day number (JDN) and adjusts the otehr dates.
     */
    fun previousDay() {
        JDN--
        JDNToIranian()
        JDNToJulian()
        JDNToGregorian()
    }

    /**
     * previousDay:
     * Overload the previousDay() method to accept the number of days to go backward
     * and adjusts the other dates accordingly.
     * @param days int
     */
    fun previousDay(days: Int) {
        JDN -= days
        JDNToIranian()
        JDNToJulian()
        JDNToGregorian()
    }

    /**
     * setIranianDate:
     * Sets the date according to the Iranian calendar and adjusts the other dates.
     * @param year int
     * @param month int
     * @param day int
     */
    fun setIranianDate(year: Int, month: Int, day: Int) {
        this.iranianYear = year
        this.iranianMonth = month
        this.iranianDay = day
        JDN = IranianDateToJDN()
        JDNToIranian()
        JDNToJulian()
        JDNToGregorian()
    }

    /**
     * setGregorianDate:
     * Sets the date according to the Gregorian calendar and adjusts the other dates.
     * @param year int
     * @param month int
     * @param day int
     */
    fun setGregorianDate(year: Int, month: Int, day: Int) {
        this.gregorianYear = year
        this.gregorianMonth = month
        this.gregorianDay = day
        JDN = gregorianDateToJDN(year, month, day)
        JDNToIranian()
        JDNToJulian()
        JDNToGregorian()
    }

    /**
     * setJulianDate:
     * Sets the date according to the Julian calendar and adjusts the other dates.
     * @param year int
     * @param month int
     * @param day int
     */
    fun setJulianDate(year: Int, month: Int, day: Int) {
        this.julianYear = year
        this.julianMonth = month
        this.julianDay = day
        JDN = julianDateToJDN(year, month, day)
        JDNToIranian()
        JDNToJulian()
        JDNToGregorian()
    }

    /**
     * IranianCalendar:
     * This method determines if the Iranian (Jalali) year is leap (366-day long)
     * or is the common year (365 days), and finds the day in March (Gregorian
     * Calendar)of the first day of the Iranian year ('irYear').Iranian year (irYear)
     * ranges from (-61 to 3177).This method will set the following private data
     * members as follows:
     * leap: Number of years since the last leap year (0 to 4)
     * Gy: Gregorian year of the begining of Iranian year
     * march: The March day of Farvardin the 1st (first day of jaYear)
     */
    private fun IranianCalendar() {
        // Iranian years starting the 33-year rule
        val Breaks: IntArray? =
            intArrayOf(
                -61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181,
                1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178
            )
        var jm: Int
        var N: Int
        var leapJ: Int
        val leapG: Int
        var jp: Int
        var j: Int
        var jump: Int
        this.gregorianYear = this.iranianYear + 621
        leapJ = -14
        jp = Breaks!![0]
        // Find the limiting years for the Iranian year 'irYear'
        j = 1
        do {
            jm = Breaks[j]
            jump = jm - jp
            if (this.iranianYear >= jm) {
                leapJ += (jump / 33 * 8 + (jump % 33) / 4)
                jp = jm
            }
            j++
        } while ((j < 20) && (this.iranianYear >= jm))
        N = this.iranianYear - jp
        // Find the number of leap years from AD 621 to the begining of the current
        // Iranian year in the Iranian (Jalali) calendar
        leapJ += (N / 33 * 8 + ((N % 33) + 3) / 4)
        if (((jump % 33) == 4) && ((jump - N) == 4)) leapJ++
        // And the same in the Gregorian date of Farvardin the first
        leapG = this.gregorianYear / 4 - ((this.gregorianYear / 100 + 1) * 3 / 4) - 150
        march = 20 + leapJ - leapG
        // Find how many years have passed since the last leap year
        if ((jump - N) < 6) N = N - jump + ((jump + 4) / 33 * 33)
        leap = (((N + 1) % 33) - 1) % 4
        if (leap == -1) leap = 4
    }

    /**
     * IranianDateToJDN:
     * Converts a date of the Iranian calendar to the Julian Day Number. It first
     * invokes the 'IranianCalender' private method to convert the Iranian date to
     * Gregorian date and then returns the Julian Day Number based on the Gregorian
     * date. The Iranian date is obtained from 'irYear'(1-3100),'irMonth'(1-12) and
     * 'irDay'(1-29/31).
     * @return long (Julian Day Number)
     */
    private fun IranianDateToJDN(): Int {
        IranianCalendar()
        return (gregorianDateToJDN(
            this.gregorianYear,
            3,
            march
        ) + (this.iranianMonth - 1) * 31 - this.iranianMonth / 7 * (this.iranianMonth - 7) + this.iranianDay - 1)
    }

    /**
     * JDNToIranian:
     * Converts the current value of 'JDN' Julian Day Number to a date in the
     * Iranian calendar. The caller should make sure that the current value of
     * 'JDN' is set correctly. This method first converts the JDN to Gregorian
     * calendar and then to Iranian calendar.
     */
    private fun JDNToIranian() {
        JDNToGregorian()
        this.iranianYear = this.gregorianYear - 621
        IranianCalendar() // This invocation will update 'leap' and 'march'
        val JDN1F = gregorianDateToJDN(this.gregorianYear, 3, march)
        var k = JDN - JDN1F
        if (k >= 0) {
            if (k <= 185) {
                this.iranianMonth = 1 + k / 31
                this.iranianDay = (k % 31) + 1
                return
            } else k -= 186
        } else {
            this.iranianYear--
            k += 179
            if (leap == 1) k++
        }
        this.iranianMonth = 7 + k / 30
        this.iranianDay = (k % 30) + 1
    }

    /**
     * julianDateToJDN:
     * Calculates the julian day number (JDN) from Julian calendar dates. This
     * integer number corresponds to the noon of the date (i.e. 12 hours of
     * Universal Time). This method was tested to be good (valid) since 1 March,
     * -100100 (of both calendars) up to a few millions (10^6) years into the
     * future. The algorithm is based on D.A.Hatcher, Q.Jl.R.Astron.Soc. 25(1984),
     * 53-55 slightly modified by K.M. Borkowski, Post.Astron. 25(1987), 275-279.
     * @param year int
     * @param month int
     * @param day int
     * @return int
     */
    private fun julianDateToJDN(year: Int, month: Int, day: Int): Int {
        return (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408
    }

    /**
     * JDNToJulian:
     * Calculates Julian calendar dates from the julian day number (JDN) for the
     * period since JDN=-34839655 (i.e. the year -100100 of both calendars) to
     * some millions (10^6) years ahead of the present. The algorithm is based on
     * D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by K.M.
     * Borkowski, Post.Astron. 25(1987), 275-279).
     */
    private fun JDNToJulian() {
        val j = 4 * JDN + 139361631
        val i = ((j % 1461) / 4) * 5 + 308
        this.julianDay = (i % 153) / 5 + 1
        this.julianMonth = ((i / 153) % 12) + 1
        this.julianYear = j / 1461 - 100100 + (8 - this.julianMonth) / 6
    }

    /**
     * gergorianDateToJDN:
     * Calculates the julian day number (JDN) from Gregorian calendar dates. This
     * integer number corresponds to the noon of the date (i.e. 12 hours of
     * Universal Time). This method was tested to be good (valid) since 1 March,
     * -100100 (of both calendars) up to a few millions (10^6) years into the
     * future. The algorithm is based on D.A.Hatcher, Q.Jl.R.Astron.Soc. 25(1984),
     * 53-55 slightly modified by K.M. Borkowski, Post.Astron. 25(1987), 275-279.
     * @param year int
     * @param month int
     * @param day int
     * @return int
     */
    private fun gregorianDateToJDN(year: Int, month: Int, day: Int): Int {
        var jdn =
            (year + (month - 8) / 6 + 100100) * 1461 / 4 + (153 * ((month + 9) % 12) + 2) / 5 + day - 34840408
        jdn = jdn - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752
        return (jdn)
    }

    /**
     * JDNToGregorian:
     * Calculates Gregorian calendar dates from the julian day number (JDN) for
     * the period since JDN=-34839655 (i.e. the year -100100 of both calendars) to
     * some millions (10^6) years ahead of the present. The algorithm is based on
     * D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by K.M.
     * Borkowski, Post.Astron. 25(1987), 275-279).
     */
    private fun JDNToGregorian() {
        var j = 4 * JDN + 139361631
        j = j + (((((4 * JDN + 183187720) / 146097) * 3) / 4) * 4 - 3908)
        val i = ((j % 1461) / 4) * 5 + 308
        this.gregorianDay = (i % 153) / 5 + 1
        this.gregorianMonth = ((i / 153) % 12) + 1
        this.gregorianYear = j / 1461 - 100100 + (8 - this.gregorianMonth) / 6
    }


    /**
     * getIranianYear:
     * Returns the 'year' part of the Iranian date.
     * @return int
     */
    var iranianYear: Int = 0 // Year part of a Iranian date
        private set

    /**
     * getIranianMonth:
     * Returns the 'month' part of the Iranian date.
     * @return int
     */
    var iranianMonth: Int = 0 // Month part of a Iranian date
        private set

    /**
     * getIranianDay:
     * Returns the 'day' part of the Iranian date.
     * @return int
     */
    var iranianDay: Int = 0 // Day part of a Iranian date
        private set

    /**
     * getGregorianYear:
     * Returns the 'year' part of the Gregorian date.
     * @return int
     */
    var gregorianYear: Int = 0 // Year part of a Gregorian date
        private set

    /**
     * getGregorianMonth:
     * Returns the 'month' part of the Gregorian date.
     * @return int
     */
    var gregorianMonth: Int = 0 // Month part of a Gregorian date
        private set

    /**
     * getGregorianDay:
     * Returns the 'day' part of the Gregorian date.
     * @return int
     */
    var gregorianDay: Int = 0 // Day part of a Gregorian date
        private set

    /**
     * getJulianYear:
     * Returns the 'year' part of the Julian date.
     * @return int
     */
    var julianYear: Int = 0 // Year part of a Julian date
        private set

    /**
     * getJulianMonth:
     * Returns the 'month' part of the Julian date.
     * @return int
     */
    var julianMonth: Int = 0 // Month part of a Julian date
        private set

    /**
     * getJulianDay()
     * Returns the 'day' part of the Julian date.
     * @return int
     */
    var julianDay: Int = 0 // Day part of a Julian date
        private set
    private var leap = 0 // Number of years since the last leap year (0 to 4)
    private var JDN = 0 // Julian Day Number
    private var march = 0 // The march day of Farvardin the first (First day of jaYear)
} // End of Class 'JavaSource_Calendar