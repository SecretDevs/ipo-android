package com.roblox.ipo.vo.remote

import com.squareup.moshi.Json

data class RemoteDeal(
    val id: String?,
    val name: String,
    val ticker: String,
    val description: String,
    @Json(name = "is_favourite") val isFavourite: Boolean?,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "updated_at") val updatedAt: String?,
    val risk: String,
    val status: String
)