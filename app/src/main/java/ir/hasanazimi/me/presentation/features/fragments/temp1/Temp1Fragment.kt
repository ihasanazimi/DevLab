package ir.hasanazimi.me.presentation.features.fragments.temp1

import android.view.animation.LinearInterpolator
import androidx.core.graphics.toColorInt
import ir.hasanazimi.me.R
import ir.hasanazimi.me.common.base.BaseFragment
import ir.hasanazimi.me.common.extensions.getAmountFormatBySeparator
import ir.hasanazimi.me.common.extensions.showToast
import ir.hasanazimi.me.common.extensions.singleClick
import ir.hasanazimi.me.common.views.segment_chart_view.SegmentChartView
import ir.hasanazimi.me.databinding.FragmentTemp1Binding

class Temp1Fragment : BaseFragment<FragmentTemp1Binding>(FragmentTemp1Binding::inflate) {

    override fun initializing() {
        super.initializing()

        val segmentChart = requireActivity().findViewById<SegmentChartView>(R.id.segmentChart)
        segmentChart.setSegments(
            listOf(
                SegmentChartView.Segment(1000.0,"#3F51B5"),
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


        binding.add.singleClick {
            val valueNumber = binding.input.text.toString()
            if (valueNumber.isNotEmpty()){
                val newColor = getRandomColorHex()
                binding.add.setBackgroundColor(newColor.toColorInt() )
                binding.segmentChart.addNewSegment(
                    SegmentChartView.Segment(
                        value = valueNumber.toDouble(),
                        color = newColor
                    )
                )
                binding.cashBackAmount.text = binding.segmentChart.getSumOfSegmentsValue().toString().getAmountFormatBySeparator()
            }else{
                showToast(requireActivity(),"please enter the new value of NewSegment")
            }
        }


        binding.clearSegments.singleClick {
            binding.segmentChart.clearAllSegments()
            binding.cashBackAmount.text = "0"
            binding.input.text?.clear()
        }
    }


    fun getRandomColorHex(): String {
        val random = java.util.Random()
        val color = String.format(
            "#%06X",
            (0xFFFFFF and random.nextInt(0xFFFFFF + 1))
        )
        return color
    }

}