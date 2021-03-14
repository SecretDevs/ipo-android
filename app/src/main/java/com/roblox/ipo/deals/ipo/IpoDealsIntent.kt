package com.roblox.ipo.deals.ipo

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class IpoDealsIntent : MviIntent {

    object InitialDealsLoadingIntent : IpoDealsIntent()

    object RetryDealsLoadingIntent : IpoDealsIntent()

    data class OpenDealDetailsIntent(
        val dealId: Long
    ) : IpoDealsIntent()

    data class ToggleDealFaveIntent(
        val dealId: Long
    ) : IpoDealsIntent()

    object FavoriteDealsNothingIntent : IpoDealsIntent(), NothingIntent

}