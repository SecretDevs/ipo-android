package com.roblox.ipo.deals.statistic

import com.roblox.ipo.base.MviAction

sealed class StatisticAction : MviAction {

    object LoadStatisticAction : StatisticAction()

    object NavigateBackAction : StatisticAction()

    data class ChangeChartRangeAction(
        val range: StatisticChartRange
    ) : StatisticAction()

}