package com.roblox.ipo.deals.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roblox.ipo.R
import com.roblox.ipo.base.recycler.EmptyViewHolder
import com.roblox.ipo.vo.inapp.IpoResult

class StatisticAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataList: MutableList<IpoResult> = mutableListOf()

    fun updateData(newData: List<IpoResult>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> 0
            else -> 1
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 -> EmptyViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_portfolio_header, parent, false)
            )
            else -> StatisticItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_portfolio, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != 0) {
            (holder as StatisticItemViewHolder).bindData(dataList[position - 1])
        }
    }

    override fun getItemCount(): Int = dataList.size + 1

}