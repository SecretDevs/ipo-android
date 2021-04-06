package com.roblox.ipo.deals.statistic

import com.roblox.ipo.base.MviEffect
import com.roblox.ipo.vo.inapp.Portfolio

sealed class StatisticEffect : MviEffect {

    object NothingEffect : StatisticEffect()

    data class LoadedDataEffect(
        val data: Portfolio
    ) : StatisticEffect()

    object LoadingEffect : StatisticEffect()

    data class LoadingErrorEffect(
        val error: Throwable
    ) : StatisticEffect()

    data class ChangedStatisticRangeEffect(
        val range: StatisticChartRange
    ) : StatisticEffect()

}