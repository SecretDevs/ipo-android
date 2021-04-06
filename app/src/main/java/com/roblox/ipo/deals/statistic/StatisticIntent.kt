package com.roblox.ipo.deals.statistic

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class StatisticIntent : MviIntent {

    object InitialIntent : StatisticIntent()

    object ReloadIntent : StatisticIntent()

    object BackArrowClickIntent : StatisticIntent()

    object StatisticNothingIntent : StatisticIntent(), NothingIntent

    data class ChangeChartRangeIntent(
        val range: StatisticChartRange
    ) : StatisticIntent()

}