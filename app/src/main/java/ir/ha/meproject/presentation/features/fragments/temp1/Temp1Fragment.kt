package ir.ha.meproject.presentation.features.fragments.temp1

import android.graphics.Color
import android.view.animation.LinearInterpolator
import ir.ha.meproject.R
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.views.segment_chart_view.SegmentChartView
import ir.ha.meproject.databinding.FragmentTemp1Binding

class Temp1Fragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    override fun initializing() {
        super.initializing()

        val segmentChart = requireActivity().findViewById<SegmentChartView>(R.id.segmentChart)
        segmentChart.setSegments(
            listOf(
                SegmentChartView.Segment(21.0, Color.MAGENTA),
                SegmentChartView.Segment(50.0, Color.CYAN),
                SegmentChartView.Segment(10.0, Color.RED),
                SegmentChartView.Segment(20.0, Color.GRAY),
                SegmentChartView.Segment(35.0, Color.LTGRAY),
                SegmentChartView.Segment(41.0, Color.BLUE),
            )
        )

        binding.segmentChart.post {
            val view = binding.segmentChart
            view.pivotX = view.width / 2f
            view.pivotY = view.height.toFloat()
            view.rotation = -180f
            view.animate()
                .rotationBy(180f)
                .setDuration(500)
                .setInterpolator(LinearInterpolator())
                .start()
        }

    }


    override fun uiConfig() {
        super.uiConfig()
    }


    override fun listeners() {
        super.listeners()
    }

}