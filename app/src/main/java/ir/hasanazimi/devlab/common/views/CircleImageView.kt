package ir.hasanazimi.devlab.common.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import ir.hasanazimi.devlab.R

class CircleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var scaleType: ScaleType = ScaleType.CENTER_CROP
    private var strokeWidth: Float = 0f
    private var strokeColor: Int = Color.TRANSPARENT
    private var cornerRadius: Float = 0f
    private var isCircular: Boolean = true

    enum class ScaleType {
        CENTER_CROP, CENTER_INSIDE
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CircleImageView,
            0, 0
        ).apply {
            try {
                scaleType = ScaleType.values()[getInt(R.styleable.CircleImageView_scaleType, 0)]
                setImageDrawable(getDrawable(R.styleable.CircleImageView_src))
                strokeWidth = getDimension(R.styleable.CircleImageView_strokeWidth, 0f)
                strokeColor = getColor(R.styleable.CircleImageView_strokeColor, Color.TRANSPARENT)
                cornerRadius = getDimension(R.styleable.CircleImageView_cornerRadius, 0f)
                isCircular = getBoolean(R.styleable.CircleImageView_isCircular, true)
            } finally {
                recycle()
            }
        }

        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = strokeColor
        strokePaint.strokeWidth = strokeWidth
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable ?: return
        val bitmap = drawable.toBitmap()
        val cx = width / 2f
        val cy = height / 2f
        val radius = Math.min(cx, cy) - strokeWidth / 2

        val scaledBitmap = getScaledBitmap(bitmap)
        val bitmapShader = BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapPaint.shader = bitmapShader

        if (isCircular) {
            canvas.drawCircle(cx, cy, radius, bitmapPaint)
            if (strokeWidth > 0) {
                canvas.drawCircle(cx, cy, radius, strokePaint)
            }
        } else {
            val rectF = RectF(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, height - strokeWidth / 2)
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, bitmapPaint)
            if (strokeWidth > 0) {
                canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, strokePaint)
            }
        }
    }

    private fun getScaledBitmap(bitmap: Bitmap): Bitmap {
        return when (scaleType) {
            ScaleType.CENTER_CROP -> {
                val scale: Float = Math.max(width.toFloat() / bitmap.width, height.toFloat() / bitmap.height)
                Bitmap.createScaledBitmap(
                    bitmap,
                    (bitmap.width * scale).toInt(),
                    (bitmap.height * scale).toInt(),
                    true
                )
            }
            ScaleType.CENTER_INSIDE -> {
                val scale: Float = Math.min(width.toFloat() / bitmap.width, height.toFloat() / bitmap.height)
                Bitmap.createScaledBitmap(
                    bitmap,
                    (bitmap.width * scale).toInt(),
                    (bitmap.height * scale).toInt(),
                    true
                )
            }
        }
    }

    fun setScaleType(scaleType: ScaleType) {
        this.scaleType = scaleType
        invalidate()
    }

    fun setStrokeWidth(width: Float) {
        strokeWidth = width
        strokePaint.strokeWidth = strokeWidth
        invalidate()
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        strokePaint.color = strokeColor
        invalidate()
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    fun setIsCircular(isCircular: Boolean) {
        this.isCircular = isCircular
        invalidate()
    }
}
