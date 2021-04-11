package com.roblox.ipo.deals.statistic

import com.roblox.ipo.base.MviViewState
import com.roblox.ipo.vo.inapp.Stats

data class StatisticViewState(
    val isLoading: Boolean,
    val errorLoading: Throwable?,
    val data: Stats?,
    val currentChartRange: StatisticChartRange
) : MviViewState {
    override fun log(): String = this.toString()
}