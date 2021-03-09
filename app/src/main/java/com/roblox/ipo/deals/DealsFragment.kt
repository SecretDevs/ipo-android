package com.roblox.ipo.deals

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_deals.*

@AndroidEntryPoint
class DealsFragment : BaseFragment<DealsViewState, DealsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_deals
    override val viewModel: DealsViewModel by viewModels()

    override fun backStackIntent(): DealsIntent = DealsIntent.DealsNothingIntent

    override fun initialIntent(): DealsIntent = DealsIntent.LoadDealsOpenStatesIntent

    override fun initViews() {
        viewpager.adapter = DealsFragmentAdapter(this)
        TabLayoutMediator(tabs_layout, viewpager) { tab, position ->
            viewpager.setCurrentItem(tab.position, true)
            if (tab.position == 0) {
                fab_statistic.hide()
            } else {
                fab_statistic.show()
            }
            if (tab.position == 3) {
                fab_statistic.extend()
            } else {
                fab_statistic.shrink()
            }
        }.attach()
    }

    override fun render(viewState: DealsViewState) {

    }

    companion object {
        fun newInstance() = DealsFragment()
    }

}