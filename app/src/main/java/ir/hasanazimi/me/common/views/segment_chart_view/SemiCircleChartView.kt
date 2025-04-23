package ir.hasanazimi.me.common.views.segment_chart_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.*

class SegmentChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val segments = mutableListOf<Segment>()
    private var spacingDegrees = 3.0
    private var innerRadiusRatio = 0.8f

    private val segmentAngles = mutableListOf<SegmentAngle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    data class Segment(
        val value: Double,
        val color: Int
    )

    data class SegmentAngle(
        val startAngle: Float,
        val endAngle: Float,
        val color: Int
    )

    fun setSegments(newList: List<Segment>) {
        segments.clear()
        segments.addAll(newList)
        computeAngles()
        invalidate()
    }

    private fun computeAngles() {
        if (segments.isEmpty()) return

        val totalValue = segments.sumByDouble { it.value }
        if (totalValue == 0.0) return

        val totalSpacing = (segments.size - 1) * spacingDegrees
        val totalAvailableDegrees = 180.0 - totalSpacing

        var startAngle = 180.0
        segmentAngles.clear()

        segments.forEach { segment ->
            val sweep = (segment.value / totalValue) * totalAvailableDegrees
            val endAngle = startAngle + sweep

            segmentAngles.add(
                SegmentAngle(
                    startAngle.toFloat(),
                    endAngle.toFloat(),
                    segment.color
                )
            )

            startAngle = endAngle + spacingDegrees
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (width == 0 || height == 0) return

        val centerX = width / 2f
        val centerY = height.toFloat() // مرکز در پایین ویو
        val radius = height.toFloat() // شعاع برابر ارتفاع

        segmentAngles.forEach { angle ->
            drawWedgeSegment(canvas, centerX, centerY, radius, angle)
        }
    }

    private fun drawWedgeSegment(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        radius: Float,
        angle: SegmentAngle
    ) {
        val path = Path()
        val startAngle = Math.toRadians(angle.startAngle.toDouble()).toFloat()
        val endAngle = Math.toRadians(angle.endAngle.toDouble()).toFloat()
        val angleDifference = endAngle - startAngle

        val innerRadius = radius * innerRadiusRatio
        val outerMiddleRadius = radius * 0.95f
        val innerMiddleRadius = radius * 0.85f

        // Outer start point
        val ossX = centerX + cos(startAngle) * outerMiddleRadius
        val ossY = centerY + sin(startAngle) * outerMiddleRadius

        // Outer start control point
        val osfAngle = startAngle + angleInRadians(radius - outerMiddleRadius, radius, angleDifference)
        val osfX = centerX + cos(osfAngle) * radius
        val osfY = centerY + sin(osfAngle) * radius

        // Outer end control point
        val oesAngle = endAngle - angleInRadians(radius - outerMiddleRadius, radius, angleDifference)
        val oesX = centerX + cos(oesAngle) * radius
        val oesY = centerY + sin(oesAngle) * radius

        // Outer end point
        val oefX = centerX + cos(endAngle) * outerMiddleRadius
        val oefY = centerY + sin(endAngle) * outerMiddleRadius

        path.moveTo(ossX, ossY)
        path.quadTo(
            centerX + cos(startAngle) * radius,
            centerY + sin(startAngle) * radius,
            osfX,
            osfY
        )

        path.arcTo(
            RectF(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
            ),
            Math.toDegrees(osfAngle.toDouble()).toFloat(),
            Math.toDegrees((oesAngle - osfAngle).toDouble()).toFloat(),
            false
        )

        path.quadTo(
            centerX + cos(endAngle) * radius,
            centerY + sin(endAngle) * radius,
            oefX,
            oefY
        )

        // Inner arc start
        val issX = centerX + cos(endAngle) * innerMiddleRadius
        val issY = centerY + sin(endAngle) * innerMiddleRadius
        path.lineTo(issX, issY)

        val isfAngle = endAngle - angleInRadians(innerMiddleRadius - innerRadius, innerRadius, angleDifference)
        val isfX = centerX + cos(isfAngle) * innerRadius
        val isfY = centerY + sin(isfAngle) * innerRadius

        path.quadTo(
            centerX + cos(endAngle) * innerRadius,
            centerY + sin(endAngle) * innerRadius,
            isfX,
            isfY
        )

        val iseAngle = startAngle + angleInRadians(innerMiddleRadius - innerRadius, innerRadius, angleDifference)
        val iseX = centerX + cos(iseAngle) * innerRadius
        val iseY = centerY + sin(iseAngle) * innerRadius

        path.arcTo(
            RectF(
                centerX - innerRadius,
                centerY - innerRadius,
                centerX + innerRadius,
                centerY + innerRadius
            ),
            Math.toDegrees(isfAngle.toDouble()).toFloat(),
            Math.toDegrees((iseAngle - isfAngle).toDouble()).toFloat(),
            false
        )

        val iesX = centerX + cos(startAngle) * innerMiddleRadius
        val iesY = centerY + sin(startAngle) * innerMiddleRadius
        path.quadTo(
            centerX + cos(startAngle) * innerRadius,
            centerY + sin(startAngle) * innerRadius,
            iesX,
            iesY
        )

        path.close()

        paint.color = angle.color
        canvas.drawPath(path, paint)
    }

    private fun angleInRadians(arcLength: Float, radius: Float, angleDifference: Float): Float {
        return (arcLength / radius).coerceAtMost(angleDifference / 2)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val maxWidth = if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED) widthSize else Int.MAX_VALUE
        val maxHeight = if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.UNSPECIFIED) heightSize else Int.MAX_VALUE

        // Maintain aspect ratio (width:height = 2:1)
        val desiredWidth = min(maxWidth, maxHeight * 2)
        val desiredHeight = desiredWidth / 2

        setMeasuredDimension(desiredWidth, desiredHeight)
    }
}