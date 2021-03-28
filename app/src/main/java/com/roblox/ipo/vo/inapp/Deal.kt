package com.roblox.ipo.vo.inapp

data class Deal(
    val id: Long,
    val name: String,
    val nameTraded: String,
    val state: Int,
    val risk: Int,
    val description: String,
    val date: Long,
    val isFavorite: Boolean
)
