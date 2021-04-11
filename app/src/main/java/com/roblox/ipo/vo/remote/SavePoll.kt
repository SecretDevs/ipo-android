package com.roblox.ipo.vo.remote

import com.squareup.moshi.Json

data class SavePoll(
    @Json(name = "risk_profile") val riskProfile: String?,
    val status: String
)