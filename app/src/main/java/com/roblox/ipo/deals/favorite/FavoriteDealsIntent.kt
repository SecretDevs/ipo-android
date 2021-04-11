package com.roblox.ipo.deals.favorite

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class FavoriteDealsIntent : MviIntent {

    object InitialDealsLoadingIntent : FavoriteDealsIntent()

    object RetryDealsLoadingIntent : FavoriteDealsIntent()

    data class OpenDealDetailsIntent(
        val dealId: String
    ) : FavoriteDealsIntent()

    object PagingDealsLoadingIntent : FavoriteDealsIntent()

    object RetryPagingDealsLoadingIntent : FavoriteDealsIntent()

    data class ToggleDealFaveIntent(
        val dealId: String,
        val newState: Boolean
    ) : FavoriteDealsIntent()

    object FavoriteDealsNothingIntent : FavoriteDealsIntent(), NothingIntent

}
