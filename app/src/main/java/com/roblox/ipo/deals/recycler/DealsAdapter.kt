package com.roblox.ipo.deals.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.roblox.ipo.R
import com.roblox.ipo.base.recycler.BaseEndlessAdapter
import com.roblox.ipo.vo.inapp.Deal

class DealsAdapter(
    private val onClick: (String) -> Unit,
    private val onFaveClick: (String, Boolean) -> Unit,
    onRetry: () -> Unit,
    onPagingRetry: () -> Unit
) : BaseEndlessAdapter<Deal, DealViewHolder>(onRetry, onPagingRetry) {

    override fun getDataViewHolder(inflater: LayoutInflater, parent: ViewGroup): DealViewHolder =
        DealViewHolder(
            inflater.inflate(R.layout.item_deal, parent, false),
            onClick,
            onFaveClick
        )

}