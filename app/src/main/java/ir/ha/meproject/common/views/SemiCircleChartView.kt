package ir.ha.meproject.common.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import ir.ha.meproject.data.ChartSegment
import kotlin.math.cos
import kotlin.math.sin

class SemiCircleChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var segments: List<ChartSegment> = emptyList()
    private var maxValue: Double = 100.0

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val segmentSpacing = 4f // degrees
    private val arcThickness = 32f // in pixels

    fun setData(data: List<ChartSegment>, max: Double) {
        this.segments = data
        this.maxValue = max
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = dpToPx(80f).toInt()
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, desiredHeight)
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (segments.isEmpty()) return

        val width = width.toFloat()
        val height = height.toFloat()

        val outerRadius = height
        val innerRadius = height - arcThickness

        val centerX = width / 2f
        val centerY = height

        val availableAngle = 180f - (segments.size - 1) * segmentSpacing
        var startAngle = 180f

        val cornerRadius = 20f

        for (segment in segments) {
            val sweepAngle = (segment.value / maxValue) * availableAngle

            val outerRect = RectF(
                centerX - outerRadius,
                centerY - outerRadius,
                centerX + outerRadius,
                centerY + outerRadius
            )
            val innerRect = RectF(
                centerX - innerRadius,
                centerY - innerRadius,
                centerX + innerRadius,
                centerY + innerRadius
            )

            val path = createRoundedSegmentPath(
                outerRect,
                innerRect,
                startAngle,
                sweepAngle.toFloat(),
                cornerRadius
            )

            paint.color = segment.color
            canvas.drawPath(path, paint)

            startAngle += sweepAngle.toFloat() + segmentSpacing
        }
    }


    private fun createRoundedSegmentPath(
        outerRect: RectF,
        innerRect: RectF,
        startAngle: Float,
        sweepAngle: Float,
        cornerRadius: Float
    ): Path {
        val path = Path()

        val outerStartAngleRad = Math.toRadians(startAngle.toDouble())
        val outerEndAngleRad = Math.toRadians((startAngle + sweepAngle).toDouble())

        val innerStartAngleRad = outerEndAngleRad
        val innerEndAngleRad = outerStartAngleRad

        val outerStartX =
            outerRect.centerX() + outerRect.width() / 2 * cos(outerStartAngleRad).toFloat()
        val outerStartY =
            outerRect.centerY() + outerRect.height() / 2 * sin(outerStartAngleRad).toFloat()

        val outerEndX =
            outerRect.centerX() + outerRect.width() / 2 * cos(outerEndAngleRad).toFloat()
        val outerEndY =
            outerRect.centerY() + outerRect.height() / 2 * sin(outerEndAngleRad).toFloat()

        val innerStartX =
            innerRect.centerX() + innerRect.width() / 2 * cos(innerStartAngleRad).toFloat()
        val innerStartY =
            innerRect.centerY() + innerRect.height() / 2 * sin(innerStartAngleRad).toFloat()

        val innerEndX =
            innerRect.centerX() + innerRect.width() / 2 * cos(innerEndAngleRad).toFloat()
        val innerEndY =
            innerRect.centerY() + innerRect.height() / 2 * sin(innerEndAngleRad).toFloat()

        path.moveTo(
            outerStartX + cornerRadius * cos(outerStartAngleRad).toFloat(),
            outerStartY + cornerRadius * sin(outerStartAngleRad).toFloat()
        )

        path.arcTo(outerRect, startAngle, sweepAngle, false)

        path.quadTo(outerEndX, outerEndY, innerStartX, innerStartY)

        path.arcTo(innerRect, (startAngle + sweepAngle), -sweepAngle, false)

        path.quadTo(
            outerStartX, outerStartY,
            outerStartX + cornerRadius * cos(outerStartAngleRad).toFloat(),
            outerStartY + cornerRadius * sin(outerStartAngleRad).toFloat()
        )

        path.close()
        return path
    }

    private fun dpToPx(dp: Float): Float =
        dp * resources.displayMetrics.density
}