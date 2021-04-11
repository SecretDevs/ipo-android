package com.roblox.ipo.vo.inapp

data class Deal(
    val id: String,
    val name: String,
    val nameTraded: String,
    val state: Int,
    val risk: Int,
    val description: String,
    val createdTime: Long,
    val updatedTime: Long?,
    val isFavorite: Boolean
)
