package com.roblox.ipo.vo.mapper

import com.roblox.ipo.vo.inapp.Statistic
import com.roblox.ipo.vo.inapp.Stats
import com.roblox.ipo.vo.remote.RemoteStatistic
import com.roblox.ipo.vo.remote.RemoteStats
import javax.inject.Inject

class StatisticMapper @Inject constructor() : Mapper<Statistic, RemoteStatistic> {
    override fun fromInappToRemote(data: Statistic): RemoteStatistic =
        RemoteStatistic(
            name = data.name,
            date = "",
            profit = data.profitPercent,
            account = data.profit
        )

    override fun fromRemoteToInapp(data: RemoteStatistic): Statistic =
        Statistic(
            name = data.name,
            date = 0L,
            profit = data.account,
            profitPercent = data.profit
        )
}

class StatsMapper @Inject constructor(
    private val statisticMapper: StatisticMapper
) : Mapper<Stats, RemoteStats> {
    override fun fromInappToRemote(data: Stats): RemoteStats =
        RemoteStats(
            stats = data.portfolioItems.map(statisticMapper::fromInappToRemote)
        )

    override fun fromRemoteToInapp(data: RemoteStats): Stats =
        Stats(
            profitPercent = data.stats.sumOf { it.profit * it.account } / data.stats.sumOf { it.account },
            totalProfit = data.stats.sumOf { it.account },
            data = data.stats.map { it.account },
            portfolioItems = data.stats.map(statisticMapper::fromRemoteToInapp)
        )
}