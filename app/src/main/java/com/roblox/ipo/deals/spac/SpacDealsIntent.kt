package com.roblox.ipo.deals.spac

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class SpacDealsIntent : MviIntent {

    object InitialDealsLoadingIntent : SpacDealsIntent()

    object RetryDealsLoadingIntent : SpacDealsIntent()

    data class OpenDealDetailsIntent(
        val dealId: Long
    ) : SpacDealsIntent()

    data class ToggleDealFaveIntent(
        val dealId: Long
    ) : SpacDealsIntent()

    object FavoriteDealsNothingIntent : SpacDealsIntent(), NothingIntent

}