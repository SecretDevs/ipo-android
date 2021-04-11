package com.roblox.ipo.deals.stocks

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent
import com.roblox.ipo.deals.spac.SpacDealsIntent

sealed class StockDealsIntent : MviIntent {

    object InitialDealsLoadingIntent : StockDealsIntent()

    object RetryDealsLoadingIntent : StockDealsIntent()

    data class OpenDealDetailsIntent(
        val dealId: String
    ) : StockDealsIntent()

    data class ToggleDealFaveIntent(
        val dealId: String,
        val newState: Boolean
    ) : StockDealsIntent()

    object FavoriteDealsNothingIntent : StockDealsIntent(), NothingIntent

    object PagingDealsLoadingIntent : StockDealsIntent()

    object RetryPagingDealsLoadingIntent : StockDealsIntent()

}