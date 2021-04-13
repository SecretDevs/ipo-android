package com.roblox.ipo.vo.inapp

data class Stats(
    val profitPercent: Long,
    val totalProfit: Long,
    val dayData: List<Statistic>,
    val weekData: List<Statistic>,
    val monthData: List<Statistic>,
    val yearData: List<Statistic>,
    val allData: List<Statistic>
)