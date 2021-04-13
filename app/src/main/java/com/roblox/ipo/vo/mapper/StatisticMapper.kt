package com.roblox.ipo.vo.mapper

import com.roblox.ipo.utils.DateConverter
import com.roblox.ipo.vo.inapp.Statistic
import com.roblox.ipo.vo.inapp.Stats
import com.roblox.ipo.vo.remote.RemoteStatistic
import com.roblox.ipo.vo.remote.RemoteStats
import javax.inject.Inject

class StatisticMapper @Inject constructor(
    private val dateConverter: DateConverter
) : Mapper<Statistic, RemoteStatistic> {
    override fun fromInappToRemote(data: Statistic): RemoteStatistic =
        RemoteStatistic(
            name = data.name,
            date = dateConverter.fromTimestampToIsoString(data.date) ?: "",
            profit = data.profitPercent,
            account = data.profit
        )

    override fun fromRemoteToInapp(data: RemoteStatistic): Statistic =
        Statistic(
            name = data.name,
            date = dateConverter.fromIsoStringToTimestamp(data.date) ?: 0L,
            profit = data.account,
            profitPercent = data.profit
        )
}

class StatsMapper @Inject constructor(
    private val statisticMapper: StatisticMapper
) : Mapper<Stats, RemoteStats> {
    override fun fromInappToRemote(data: Stats): RemoteStats =
        RemoteStats(
            stats = data.allData.map(statisticMapper::fromInappToRemote)
        )

    override fun fromRemoteToInapp(data: RemoteStats): Stats {
        val currentTimestamp = System.currentTimeMillis()
        val allData = data.stats.map(statisticMapper::fromRemoteToInapp)
        return Stats(
            profitPercent = data.stats.sumOf { it.profit * it.account } / data.stats.sumOf { it.account },
            totalProfit = data.stats.sumOf { it.account },
            dayData = allData.filter { it.date > currentTimestamp - DAY_MILLIS },
            weekData = allData.filter { it.date > currentTimestamp - WEEK_MILLIS },
            monthData = allData.filter { it.date > currentTimestamp - MONTH_MILLIS },
            yearData = allData.filter { it.date > currentTimestamp - YEAR_MILLIS },
            allData = allData
        )
    }

    companion object {
        private const val DAY_MILLIS = 24 * 60 * 60 * 1000L
        private const val WEEK_MILLIS = 7 * DAY_MILLIS
        private const val MONTH_MILLIS = 30 * WEEK_MILLIS
        private const val YEAR_MILLIS = 365 * DAY_MILLIS
    }

}