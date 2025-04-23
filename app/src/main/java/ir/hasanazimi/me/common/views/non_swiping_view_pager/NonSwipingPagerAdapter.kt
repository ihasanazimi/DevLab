package ir.hasanazimi.me.common.views.non_swiping_view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/** Worked by ViewPager2 */
class NonSwipingPagerAdapter(fa: FragmentActivity, private val fragments: List<Fragment>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
