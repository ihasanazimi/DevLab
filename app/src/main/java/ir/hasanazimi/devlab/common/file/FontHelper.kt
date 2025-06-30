package ir.hasanazimi.devlab.common.file

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat

object FontHelper {
    fun getTypeface(context: Context, fontResId: Int): Typeface? {
        return ResourcesCompat.getFont(context, fontResId)
    }
}