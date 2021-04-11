package com.roblox.ipo.deals.favorite

import com.roblox.ipo.base.MviEffect
import com.roblox.ipo.vo.inapp.Deal

sealed class FavoriteDealsEffect : MviEffect {

    object InitialLoadingEffect : FavoriteDealsEffect()

    data class InitialLoadingErrorEffect(
        val error: Throwable
    ) : FavoriteDealsEffect()

    data class DealsLoadedEffect(
        val deals: List<Deal>
    ) : FavoriteDealsEffect()

    object PagingLoadingEffect : FavoriteDealsEffect()

    data class PagingLoadingErrorEffect(
        val error: Throwable
    ) : FavoriteDealsEffect()

    data class PagingDealsLoadedEffect(
        val deals: List<Deal>
    ) : FavoriteDealsEffect()

    object NothingEffect : FavoriteDealsEffect()

}