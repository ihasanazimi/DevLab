package ir.ha.meproject.presentation.features.fragments.temp1

import android.graphics.Color
import ir.ha.meproject.R
import ir.ha.meproject.common.base.BaseFragment
import ir.ha.meproject.common.views.SemiCircleChartView
import ir.ha.meproject.data.ChartSegment
import ir.ha.meproject.databinding.FragmentTemp1Binding

class Temp1Fragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    override fun initializing() {
        super.initializing()

        val chartView = requireActivity().findViewById<SemiCircleChartView>(R.id.semiCircleChartView)

        val temp = listOf(
            ChartSegment(20.0, Color.YELLOW),
            ChartSegment(12.0, Color.GRAY),
            ChartSegment(40.0, Color.RED),
            ChartSegment(12.0, Color.GRAY),
            ChartSegment(40.0, Color.WHITE),
            ChartSegment(12.0, Color.GRAY),
            ChartSegment(10.0, Color.GRAY),
            ChartSegment(4.0, Color.WHITE),
        )

        val max = temp.sumOf { it.value }
        chartView.setData(
            temp,
            max = max
        )

    }


    override fun uiConfig() {
        super.uiConfig()
    }


    override fun listeners() {
        super.listeners()
    }

}