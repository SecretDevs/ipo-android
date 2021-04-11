package com.roblox.ipo.deals.ipo

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class IpoDealsIntent : MviIntent {

    object InitialDealsLoadingIntent : IpoDealsIntent()

    object RetryDealsLoadingIntent : IpoDealsIntent()

    data class OpenDealDetailsIntent(
        val dealId: String
    ) : IpoDealsIntent()

    data class ToggleDealFaveIntent(
        val dealId: String,
        val newState: Boolean
    ) : IpoDealsIntent()

    object FavoriteDealsNothingIntent : IpoDealsIntent(), NothingIntent

    object PagingDealsLoadingIntent : IpoDealsIntent()

    object RetryPagingDealsLoadingIntent : IpoDealsIntent()

}