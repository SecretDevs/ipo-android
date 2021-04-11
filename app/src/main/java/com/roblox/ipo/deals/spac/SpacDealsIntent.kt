package com.roblox.ipo.deals.spac

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent
import com.roblox.ipo.deals.favorite.FavoriteDealsIntent

sealed class SpacDealsIntent : MviIntent {

    object InitialDealsLoadingIntent : SpacDealsIntent()

    object RetryDealsLoadingIntent : SpacDealsIntent()

    data class OpenDealDetailsIntent(
        val dealId: String
    ) : SpacDealsIntent()

    object PagingDealsLoadingIntent : SpacDealsIntent()

    object RetryPagingDealsLoadingIntent : SpacDealsIntent()

    data class ToggleDealFaveIntent(
        val dealId: String,
        val newState: Boolean
    ) : SpacDealsIntent()

    object FavoriteDealsNothingIntent : SpacDealsIntent(), NothingIntent

}