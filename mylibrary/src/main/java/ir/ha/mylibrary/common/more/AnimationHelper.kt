package ir.ha.mylibrary.common.more

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

/**
 * Created by ihasan.azimi@gmail.com on 5/7/15.
 */

private const val TAG_ANIMATION = "AnimationHelper"


object AnimationHelper {

    fun View.enableHardwareLayer() {
        // Enable hardware layer
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null)
    }

    fun View.disableHardwareLayer() {
        // Restore to default layer type (none or software)
        this.setLayerType(View.LAYER_TYPE_NONE, null)
    }



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




    // انیمیشن چرخش با ObjectAnimator از androidx
    fun rotate(view: View, duration: Long, fromDegrees: Float, toDegrees: Float) {
        val rotation = ObjectAnimator.ofFloat(view, View.ROTATION, fromDegrees, toDegrees)
        rotation.duration = duration
        rotation.interpolator = AccelerateDecelerateInterpolator()
        rotation.start()
    }

    // انیمیشن جابجایی با ObjectAnimator از androidx
    fun translate(view: View, duration: Long, fromX: Float, toX: Float, fromY: Float, toY: Float) {
        val translateX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, fromX, toX)
        val translateY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, fromY, toY)
        translateX.duration = duration
        translateY.duration = duration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translateX, translateY)
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.start()
    }

    // انیمیشن تغییر مقیاس با ObjectAnimator از androidx
    fun scale(view: View, duration: Long, fromScaleX: Float, toScaleX: Float, fromScaleY: Float, toScaleY: Float) {
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, fromScaleX, toScaleX)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, fromScaleY, toScaleY)
        scaleX.duration = duration
        scaleY.duration = duration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.start()
    }

    // انیمیشن محو شدن با ObjectAnimator از androidx
    fun fade(view: View, duration: Long, fromAlpha: Float, toAlpha: Float) {
        val fadeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, fromAlpha, toAlpha)
        fadeAnimator.duration = duration
        fadeAnimator.interpolator = LinearInterpolator()
        fadeAnimator.start()
    }

    // ترکیب چند انیمیشن (چرخش، جابجایی و مقیاس) با ObjectAnimator
    fun combinedAnimation(view: View, duration: Long) {
        val rotate = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 360f)
        val translateX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f, 200f)
        val translateY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, 200f)
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 1.5f)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 1.5f)

        rotate.duration = duration
        translateX.duration = duration
        translateY.duration = duration
        scaleX.duration = duration
        scaleY.duration = duration

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(rotate, translateX, translateY, scaleX, scaleY)
        animatorSet.interpolator = BounceInterpolator()
        animatorSet.start()
    }

    // ترکیب جابجایی و محو شدن با ObjectAnimator
    fun translateAndFade(view: View, duration: Long, fromX: Float, toX: Float, fromY: Float, toY: Float, fromAlpha: Float, toAlpha: Float) {
        val translateX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, fromX, toX)
        val translateY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, fromY, toY)
        val fadeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, fromAlpha, toAlpha)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translateX, translateY, fadeAnimator)
        animatorSet.duration = duration
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.start()
    }




    fun mergeAnimations(view: View, duration: Long, animations: List<Animator>) {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animations) // پاس دادن لیست انیمیشن‌ها
        animatorSet.duration = duration
        animatorSet.start()
    }


    // مثالی از یک انیمیشن چرخش
    fun createRotateAnimation(view: View, fromDegrees: Float, toDegrees: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.ROTATION, fromDegrees, toDegrees)
    }

    // مثالی از یک انیمیشن مقیاس
    fun createScaleAnimation(view: View, fromScaleX: Float, toScaleX: Float, fromScaleY: Float, toScaleY: Float): AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, fromScaleX, toScaleX)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, fromScaleY, toScaleY)
        return AnimatorSet().apply {
            playTogether(scaleX, scaleY)
        }
    }

    // مثالی از یک انیمیشن محو شدن
    fun createFadeAnimation(view: View, fromAlpha: Float, toAlpha: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, fromAlpha, toAlpha)
    }



}






