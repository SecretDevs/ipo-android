package com.roblox.ipo.deals.statistic

import com.roblox.ipo.base.MviViewState
import com.roblox.ipo.vo.inapp.Portfolio

data class StatisticViewState(
    val isLoading: Boolean,
    val errorLoading: Throwable?,
    val data: Portfolio?,
    val currentChartRange: StatisticChartRange
) : MviViewState {
    override fun log(): String = this.toString()
}