package com.roblox.ipo.deals

import androidx.core.content.ContextCompat
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

    private lateinit var fragmentAdapter: DealsFragmentAdapter

    override fun backStackIntent(): DealsIntent = DealsIntent.DealsNothingIntent

    override fun initialIntent(): DealsIntent = DealsIntent.LoadDealsOpenStatesIntent

    override fun initViews() {
        fragmentAdapter = DealsFragmentAdapter(this)
        viewpager.adapter = fragmentAdapter
        TabLayoutMediator(tabs_layout, viewpager) { tab, position ->
            viewpager.setCurrentItem(tab.position, true)
            tab.setText(
                when (position) {
                    0 -> R.string.title_fragment_deals_tab_favorite
                    1 -> R.string.title_fragment_deals_tab_ipo
                    2 -> R.string.title_fragment_deals_tab_spac
                    3 -> R.string.title_fragment_deals_tab_stocks
                    else -> throw IllegalStateException("too much tabs")
                }
            )
            if (position == 0) {
                tab.setIcon(R.drawable.ic_faved_deal)
                tab.icon?.setTint(ContextCompat.getColor(requireContext(), R.color.colorOrange))
            }
        }.attach()
    }

    override fun render(viewState: DealsViewState) {
        fragmentAdapter.updateStates(viewState.states)
        viewState.states.forEachIndexed { index, state ->
            if (!state) {
                tabs_layout.getTabAt(index)?.setIcon(R.drawable.ic_locked_section)
            }
        }
    }

    companion object {
        fun newInstance() = DealsFragment()
    }

}