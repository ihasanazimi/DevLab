package ir.ha.mylibrary.common.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ir.ha.mylibrary.common.file.FontHelper

class CircleProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    interface OnProgressChangedListener {
        fun onProgressChanged(progress: Int)
    }

    private var progressChangedListener: OnProgressChangedListener? = null

    private var progress: Int = 0
    private var maxProgress: Int = 100
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val circleBounds = RectF()
    private val defaultSize = 150f // Default size if no constraints are provided

    private var activeProgressTintColor = ContextCompat.getColor(context, android.R.color.holo_blue_light)
    private var progressTintColor = ContextCompat.getColor(context, android.R.color.darker_gray)
    private var progressTextColor = ContextCompat.getColor(context, android.R.color.holo_blue_light)
    private var progressTextSize : Float= 33f
    private var progressTypeFace = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    private var animatedProgressText: String = "0%"

    init {
        circlePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 14F
            color = ContextCompat.getColor(context, android.R.color.holo_blue_light)
            strokeCap = Paint.Cap.ROUND // Set the stroke cap to round
        }

        textPaint.apply {
            color = progressTextColor
            textAlign = Paint.Align.CENTER
            textSize = progressTextSize
            typeface = progressTypeFace
        }
    }

    fun setProgress(progress: Int, animate: Boolean = true, duration: Long = 600) {
        if (animate) {
            animateProgress(progress, duration)
        } else {
            this.progress = progress
            invalidate()
        }
    }

    private fun animateProgress(toProgress: Int, duration: Long = 300) {
        val progressAnimator = ValueAnimator.ofInt(progress, toProgress)
        progressAnimator.duration = duration
        progressAnimator.addUpdateListener { animation ->
            progress = animation.animatedValue as Int
            invalidate() // Redraw the view
        }

        val textAnimator = ValueAnimator.ofInt(progress, toProgress)
        textAnimator.duration = duration
        textAnimator.addUpdateListener { animation ->
            animatedProgressText = "${animation.animatedValue as Int}%"
            invalidate() // Redraw the view
        }

        progressAnimator.start()
        textAnimator.start()
    }

    fun configProgressBar(
        activeProgressTintColor: Int? = null,
        progressTintColor: Int? = null,
        progressTextColor: Int? = null,
        progressTextSize: Float? = null,
        progressFont: Int? = null,
    ) {
        this.activeProgressTintColor = activeProgressTintColor?.let { ContextCompat.getColor(context, it) } ?: ContextCompat.getColor(context, android.R.color.holo_blue_light)
        this.progressTintColor = progressTintColor?.let { ContextCompat.getColor(context, it) } ?: Color.GRAY
        this.progressTextColor = progressTextColor?.let { ContextCompat.getColor(context, it) } ?: ContextCompat.getColor(context, android.R.color.holo_blue_light)
        this.progressTextSize = progressTextSize ?: 30f
        this.progressTypeFace = progressFont?.let { FontHelper.getTypeface(context, it) }
        invalidate()
    }

    fun lowProgress(duration: Long = 300) {
        if (progress > 0) {
            animateProgress(progress - 1, duration)
            progressChangedListener?.onProgressChanged(progress - 1)
        }
    }

    fun addProgress(duration: Long = 300) {
        if (progress < 100) {
            animateProgress(progress + 1, duration)
        }
        progressChangedListener?.onProgressChanged(progress + 1)
    }


    fun setOnProgressChangedListener(listener: OnProgressChangedListener) {
        progressChangedListener = listener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = (Math.min(width.toFloat(), height.toFloat()) / 2) - circlePaint.strokeWidth
        val centerX = width / 2f
        val centerY = height / 2f

        circleBounds.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        // Draw the background circle
        circlePaint.color = progressTintColor
        canvas.drawCircle(centerX, centerY, radius, circlePaint)

        // Draw the progress circle
        circlePaint.color = activeProgressTintColor
        val sweepAngle = 360 * progress / maxProgress.toFloat()
        canvas.drawArc(circleBounds, -90f, sweepAngle, false, circlePaint)

        // Draw the central label
        canvas.drawText(animatedProgressText, centerX, centerY - (textPaint.descent() + textPaint.ascent()) / 2, textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = defaultSize.toInt() + paddingLeft + paddingRight
        val desiredHeight = defaultSize.toInt() + paddingTop + paddingBottom

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> desiredWidth.coerceAtMost(widthSize)
            else -> desiredWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(heightSize)
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }
}
