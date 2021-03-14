package com.roblox.ipo.deals.stocks

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class StockDealsIntent : MviIntent {

    object InitialDealsLoadingIntent : StockDealsIntent()

    object RetryDealsLoadingIntent : StockDealsIntent()

    data class OpenDealDetailsIntent(
        val dealId: Long
    ) : StockDealsIntent()

    data class ToggleDealFaveIntent(
        val dealId: Long
    ) : StockDealsIntent()

    object FavoriteDealsNothingIntent : StockDealsIntent(), NothingIntent

}