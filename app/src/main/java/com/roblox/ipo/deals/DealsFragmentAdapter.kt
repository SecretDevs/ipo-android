package com.roblox.ipo.deals

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.roblox.ipo.deals.favorite.FavoriteDealsFragment
import com.roblox.ipo.deals.ipo.IpoDealsFragment
import com.roblox.ipo.deals.spac.SpacDealsFragment
import com.roblox.ipo.deals.stocks.StockDealsFragment

class DealsFragmentAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    val states: List<Boolean>
        get() = _states
    private val _states = DEFAULT_STATE

    fun updateStates(states: List<Boolean>) {
        if (states.isEmpty()) {
            return
        }
        this._states.clear()
        this._states.addAll(states)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavoriteDealsFragment.newInstance()
            1 -> if (_states[position]) IpoDealsFragment.newInstance() else LockedDealFragment.newInstance(position, 12000)
            2 -> if (_states[position]) SpacDealsFragment.newInstance() else LockedDealFragment.newInstance(position, 12000)
            3 -> if (_states[position]) StockDealsFragment.newInstance() else LockedDealFragment.newInstance(position, 12000)
            else -> throw IllegalStateException("too much tabs")
        }

    companion object {
        private val DEFAULT_STATE = mutableListOf(true, false, false, false)
    }

}