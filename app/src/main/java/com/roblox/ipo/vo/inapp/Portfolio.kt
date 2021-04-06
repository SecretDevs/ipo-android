package com.roblox.ipo.vo.inapp

data class Portfolio(
    val profitPercent: Int,
    val totalProfit: Int,
    val data: List<Int>,
    val portfolioItems: List<IpoResult>
)