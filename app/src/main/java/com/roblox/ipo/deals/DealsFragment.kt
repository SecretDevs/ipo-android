package com.roblox.ipo.deals

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_deals.*
import kotlinx.android.synthetic.main.scene_deals_end_fab.view.*


@AndroidEntryPoint
class DealsFragment : BaseFragment<DealsViewState, DealsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_deals
    override val viewModel: DealsViewModel by viewModels()

    private lateinit var fragmentAdapter: DealsFragmentAdapter
    private lateinit var transitionManager: TransitionManager
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene

    private val tabChangedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            changeFabState(tab?.position ?: 0)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabReselected(tab: TabLayout.Tab?) {}
    }

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
        }.attach()
        tabs_layout.addOnTabSelectedListener(tabChangedListener)

        transitionManager = TransitionManager()
        scene1 =
            Scene.getSceneForLayout(motion_layout, R.layout.scene_deals_start_fab, requireContext())
        scene2 =
            Scene.getSceneForLayout(motion_layout, R.layout.scene_deals_end_fab, requireContext())
        val transition: Transition =
            TransitionInflater.from(context).inflateTransition(R.transition.transition_arc)
        transitionManager.setTransition(scene1, scene2, transition)
        transitionManager.setTransition(scene2, scene1, transition)

        changeFabState(tabs_layout.selectedTabPosition)
    }

    override fun render(viewState: DealsViewState) {
        fragmentAdapter.updateStates(viewState.states)
        viewState.states.forEachIndexed { index, state ->
            if (!state) {
                tabs_layout.getTabAt(index)?.setIcon(R.drawable.ic_locked_section)
            }
        }
        changeFabState(tabs_layout.selectedTabPosition)
    }

    private fun changeFabState(position: Int) {
        motion_layout.isVisible = position != 0
        if (position == 0) {
            isCurrentLockedSection = null
        } else {
            if (fragmentAdapter.states[position]) {
                if (isCurrentLockedSection != false) {
                    transitionManager.transitionTo(scene1)
                    scene1.sceneRoot.fab_statistic.shrink()
                }
            } else {
                if (isCurrentLockedSection != true) {
                    transitionManager.transitionTo(scene2)
                    scene2.sceneRoot.fab_statistic.extend()
                }
            }
            isCurrentLockedSection = !fragmentAdapter.states[position]
        }
    }

    override fun onStop() {
        super.onStop()
        tabs_layout.removeOnTabSelectedListener(tabChangedListener)
    }

    companion object {
        private var isCurrentLockedSection: Boolean? = null

        fun newInstance() = DealsFragment()
    }

}