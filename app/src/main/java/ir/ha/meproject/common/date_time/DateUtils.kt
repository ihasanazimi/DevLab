package ir.ha.meproject.common.date_time

import android.content.Context
import java.util.*

/*
 * *
 *  * Created by Hasan Azimi on 1/8/22, 4:25 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 1/8/22, 4:25 PM
 *
 */

object DateUtils {

    fun Date?.timeAgo(context: Context): String {
        val sec = 1000L
        val min = sec * 60
        val hour = min * 60
        val day = hour * 24
        val week = day * 7
        val diff = Date().time - (this?.time ?:Date().time)
        return when {
            diff <= day -> "امروز"
            diff < week -> (diff / day).toInt().toString() + "روز" +" "+ "قبل"
            else -> (diff / week).toInt().toString() + "هفته" +" "+ "قبل"
        }

    }

}