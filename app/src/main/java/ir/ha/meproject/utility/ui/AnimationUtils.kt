package ir.ha.meproject.utility.ui
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.DecelerateInterpolator
import androidx.core.animation.ObjectAnimator

/**
 * Created by ihasan.azimi@gmail.com on 5/7/15.
 */

private const val TAG_ANIMATION = "CIRCULAR_ANIM"

fun View.startCircularReveal(
    centerPosition: IntArray,
    duration: Long = 300L,
    color: Int = Color.GREEN,
    allowStartAnimator: (() -> Boolean),
    onStart: (() -> Unit)? = null,
    beforeEnd: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null,
    onCancellation: (() -> Unit)? = null,
    ) {
    if (centerPosition.size < 2) {
        throw IllegalArgumentException("centerPosition array must have at least two elements for X and Y coordinates")
    }

    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            v?.removeOnLayoutChangeListener(this)

            if (allowStartAnimator?.invoke() == false) {
                return
            }

            val finalRadius = Math.hypot(width.toDouble(), height.toDouble()).toFloat()
            val centerX = centerPosition[0]
            val centerY = centerPosition[1]

            val circularReveal = ViewAnimationUtils.createCircularReveal(this@startCircularReveal, centerX, centerY, 0f, finalRadius)
            circularReveal.duration = duration

            setBackgroundColor(color)

            val finalizeDelay = duration / 3

            val animator = ValueAnimator.ofFloat(0f, 1f)
            animator.duration = finalizeDelay
            animator.addUpdateListener {
                if (it.animatedFraction >= 1f) {
                    beforeEnd?.invoke()
                    animator.cancel() // Stop the animator after onFinalize is invoked
                }
            }

            circularReveal.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    Log.i(TAG_ANIMATION, "onAnimationStart: ")
                    onStart?.invoke()
                    animator.start() // Start the ValueAnimator when the circular reveal starts
                    this@startCircularReveal.enableHardwareLayer()
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    Log.i(TAG_ANIMATION, "onAnimationEnd: ")
                    onEnd?.invoke()
                    this@startCircularReveal.disableHardwareLayer()
                }

                override fun onAnimationCancel(animation: Animator) {
                    super.onAnimationCancel(animation)
                    onCancellation?.invoke()
                    this@startCircularReveal.disableHardwareLayer()
                }


            })

            circularReveal.start()
        }
    })
}





fun View.closeCircularReveal(
    centerPosition: IntArray,
    duration: Long = 300L,
    color: Int = Color.TRANSPARENT,
    allowStartAnimator: (() -> Boolean),
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null,
    onCancellation: (() -> Unit)? = null,
) {
    if (centerPosition.size < 2) {
        throw IllegalArgumentException("centerPosition array must have at least two elements for X and Y coordinates")
    }

    // Optional: Trigger the allowStartAnimator callback and check its result
    if (allowStartAnimator?.invoke() == false) {
        return
    }

    val finalRadius = Math.hypot(width.toDouble(), height.toDouble()).toFloat()
    val centerX = centerPosition[0]
    val centerY = centerPosition[1]

    // Create the circular reveal animation
    val circularReveal = ViewAnimationUtils.createCircularReveal(this, centerX, centerY, finalRadius, 0f).apply {
        this.duration = duration
    }

    circularReveal.addListener(object : AnimatorListenerAdapter() {

        override fun onAnimationStart(animation: Animator) {
            Log.i(TAG_ANIMATION, "onAnimationStart: ")
            onStart?.invoke()
            this@closeCircularReveal.enableHardwareLayer()
        }

        override fun onAnimationEnd(animation: Animator) {
            Log.i(TAG_ANIMATION, "onAnimationEnd: ")
            setBackgroundColor(color)
            onEnd?.invoke()
            this@closeCircularReveal.disableHardwareLayer()
        }

        override fun onAnimationCancel(animation: Animator) {
            super.onAnimationCancel(animation)
            onCancellation?.invoke()
            this@closeCircularReveal.disableHardwareLayer()
        }
    })

    // Start the circular reveal animation
    circularReveal.start()
}




fun View.animateFromBottomToTop(
    startOffset: Float = 600f,
    duration: Long = 500L,
    startDelay: Long = 100,
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null,
    onCancellation: (() -> Unit)? = null
    ) {


    // Set initial state for translation
    this.translationY = startOffset

    // Translation animation (from startOffset to its original position)
    val translateAnimator = ObjectAnimator.ofFloat(this, "translationY", 0f).apply {
        this.duration = duration
        this.startDelay = startDelay
        interpolator = DecelerateInterpolator() // Makes the animation smooth
    }

    // Start the animation
    translateAnimator.addListener(object : AnimatorListenerAdapter(), androidx.core.animation.Animator.AnimatorListener {

        override fun onAnimationStart(animation: androidx.core.animation.Animator) {
            Log.i(TAG_ANIMATION, "onAnimationStart: ")
            onStart?.invoke()
            this@animateFromBottomToTop.enableHardwareLayer()
        }

        override fun onAnimationEnd(animation: androidx.core.animation.Animator) {
            Log.i(TAG_ANIMATION, "onAnimationEnd: ")
            onEnd?.invoke()
            this@animateFromBottomToTop.disableHardwareLayer()
        }

        override fun onAnimationCancel(animation: androidx.core.animation.Animator) {
            Log.i(TAG_ANIMATION, "onAnimationCancel: ")
            onCancellation?.invoke()
            this@animateFromBottomToTop.disableHardwareLayer()
        }

        override fun onAnimationRepeat(animation: androidx.core.animation.Animator) {
            Log.i(TAG_ANIMATION, "onAnimationRepeat: ")
        }

    })
    translateAnimator.start()
}




fun View.enableHardwareLayer() {
    // Enable hardware layer
    this.setLayerType(View.LAYER_TYPE_HARDWARE, null)
}

fun View.disableHardwareLayer() {
    // Restore to default layer type (none or software)
    this.setLayerType(View.LAYER_TYPE_NONE, null)
}

fun Float.dpToPx(context: Context): Float {
    return this * context.resources.displayMetrics.density
}


