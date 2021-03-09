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

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavoriteDealsFragment.newInstance()
            1 -> IpoDealsFragment.newInstance()
            2 -> SpacDealsFragment.newInstance()
            3 -> StockDealsFragment.newInstance()
            else -> throw IllegalStateException("too much tabs")
        }
}