package app.netlify.dev4rju9.myapplication.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.netlify.dev4rju9.myapplication.fragments.DashboardFragment
import app.netlify.dev4rju9.myapplication.fragments.WorkshopFragment


class ViewPagerAdapter (fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> {
                WorkshopFragment()
            }
            else -> {
                DashboardFragment()
            }
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}
