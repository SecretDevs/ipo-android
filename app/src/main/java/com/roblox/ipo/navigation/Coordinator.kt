package com.roblox.ipo.navigation

import androidx.fragment.app.FragmentActivity
import com.roblox.ipo.R
import com.roblox.ipo.deals.DealsFragment
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

interface Coordinator {
    fun navigateToDeals()
    fun start()
    fun pop()
}

@ActivityScoped
class CoordinatorImpl @Inject constructor(
    @ActivityScoped activity: FragmentActivity
) : Coordinator {
    private val fragmentManager = activity.supportFragmentManager

    override fun navigateToDeals() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DealsFragment.newInstance())
            .commitAllowingStateLoss()
    }

    override fun start() {
        navigateToDeals()
    }

    override fun pop() {
        fragmentManager.popBackStack()
    }

    private fun clearBackStack() {
        repeat(fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

}