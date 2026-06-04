package ir.hasanazimi.androidlab.common.helpers.extension_helpers

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun isColorLight(color : Int): Boolean {
    val darkness = 1 - (0.299 * Color.red(color) +
            0.587 * Color.green(color) +
            0.114 * Color.blue(color)) / 255
    return darkness < 0.5
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