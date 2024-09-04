package ir.ha.meproject.utility.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import ir.ha.meproject.R

class SnackBarUtil {

    companion object {
        @SuppressLint("RestrictedApi")
        fun showSnackBar(
            activity: Activity,
            message: String,
            @DrawableRes iconRes: Int,
            duration: Int = Snackbar.LENGTH_LONG
        ) {
            val rootView = activity.findViewById<View>(android.R.id.content) as ViewGroup
            val snackbar = Snackbar.make(rootView, "", duration)
            snackbar.setBackgroundTint(Color.TRANSPARENT)

            val customView = LayoutInflater.from(activity).inflate(R.layout.layout_snack_bar, null)

            val iconView = customView.findViewById<ImageView>(R.id.snackbar_icon)
            val textView = customView.findViewById<TextView>(R.id.snackbar_text)

            iconView.setImageResource(iconRes)
            textView.text = message

            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
            snackbarLayout.setPadding(0, 0, 0, 0)
            snackbarLayout.addView(customView, 0)

            val parentParams = snackbarLayout.layoutParams as FrameLayout.LayoutParams
            parentParams.gravity = Gravity.TOP
            parentParams.setMargins(16, dpToPx(activity, 32), 16, 0) // تنظیم مارجین از بالا
            snackbarLayout.layoutParams = parentParams

            snackbar.show()
        }

        fun showSuccessSnackBar(activity: Activity, @StringRes messageRes: Int) {
            showSnackBar(
                activity,
                activity.getString(messageRes),
                iconRes = R.drawable.baseline_done_24, // آیکون موفقیت
                duration = Snackbar.LENGTH_LONG
            )
        }

        fun showErrorSnackBar(activity: Activity, @StringRes messageRes: Int) {
            showSnackBar(
                activity,
                activity.getString(messageRes),
                iconRes = R.drawable.baseline_error_outline_24, // آیکون خطا
                duration = Snackbar.LENGTH_LONG
            )
        }

        fun showWarningSnackBar(activity: Activity, @StringRes messageRes: Int) {
            showSnackBar(
                activity,
                activity.getString(messageRes),
                iconRes = R.drawable.baseline_error_outline_24, // آیکون هشدار
                duration = Snackbar.LENGTH_LONG
            )
        }


        private fun dpToPx(activity: Activity, dp: Int): Int {
            val density = activity.resources.displayMetrics.density
            return (dp * density).toInt()
        }

    }
}
