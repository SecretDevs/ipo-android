package com.roblox.ipo.vo.inapp

data class Stats(
    val profitPercent: Long,
    val totalProfit: Long,
    val data: List<Long>,
    val portfolioItems: List<Statistic>
)