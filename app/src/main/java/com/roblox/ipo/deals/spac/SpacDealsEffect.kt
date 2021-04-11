package com.roblox.ipo.deals.spac

import com.roblox.ipo.base.MviEffect
import com.roblox.ipo.vo.inapp.Deal

sealed class SpacDealsEffect : MviEffect {

    object InitialLoadingEffect : SpacDealsEffect()

    data class InitialLoadingErrorEffect(
        val error: Throwable?
    ) : SpacDealsEffect()

    data class DealsLoadedEffect(
        val deals: List<Deal>
    ) : SpacDealsEffect()

    object NothingEffect : SpacDealsEffect()

    object PagingLoadingEffect : SpacDealsEffect()

    data class PagingLoadingErrorEffect(
        val error: Throwable
    ) : SpacDealsEffect()

    data class PagingDealsLoadedEffect(
        val deals: List<Deal>
    ) : SpacDealsEffect()


}