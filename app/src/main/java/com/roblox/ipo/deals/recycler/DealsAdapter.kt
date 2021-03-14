package com.roblox.ipo.deals.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.roblox.ipo.R
import com.roblox.ipo.base.recycler.BaseStateAdapter
import com.roblox.ipo.vo.inapp.Deal

class DealsAdapter(
    private val onClick: (Long) -> Unit,
    private val onFaveClick: (Long) -> Unit,
    onRetry: () -> Unit
) : BaseStateAdapter<Deal, DealViewHolder>(onRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): DealViewHolder =
        DealViewHolder(
            inflater.inflate(R.layout.item_deal, parent, false),
            onClick,
            onFaveClick
        )

}