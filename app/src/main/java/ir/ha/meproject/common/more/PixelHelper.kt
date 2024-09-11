package ir.ha.meproject.common.more

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.NonNull

/**
 * Util class for converting between dp, px and other magical pixel units
 */
object PixelHelper {

    @JvmStatic
    fun dpToPx(dp: Float, context: Context): Int {
        return dpToPx(context, dp)
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Int): Int {
        return dpToPx(context, dp.toFloat())
    }

    @JvmStatic
    fun dpToPx(context: Context, dp: Float): Int {
        return Math.round(dp * getPixelScaleFactor(context))
    }

    @JvmStatic
    fun spToPx(sp: Float, @NonNull context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics).toInt()
    }

    @JvmStatic
    fun pxToDp(px: Float, context: Context): Float {
        return px / getPixelScaleFactor(context)
    }

    private fun getPixelScaleFactor(context: Context): Float {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT
    }

    @JvmStatic
    fun getWidth(@NonNull context: Context): Int {
        return getWidthPx(context)
    }

    @JvmStatic
    fun getWidthPx(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    @JvmStatic
    fun getHeight(@NonNull context: Context): Int {
        return getHeightPx(context)
    }

    @JvmStatic
    fun getHeightPx(@NonNull context: Context): Int {
        return getHeightPx_2(context)
    }

    @JvmStatic
    fun getHeightPx_2(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }
}
