package com.roblox.ipo.deals.ipo

import com.roblox.ipo.base.MviEffect
import com.roblox.ipo.vo.inapp.Deal

sealed class IpoDealsEffect : MviEffect {

    object InitialLoadingEffect : IpoDealsEffect()

    data class InitialLoadingErrorEffect(
        val error: Throwable?
    ) : IpoDealsEffect()

    data class DealsLoadedEffect(
        val deals: List<Deal>
    ) : IpoDealsEffect()

    object NothingEffect : IpoDealsEffect()

    object PagingLoadingEffect : IpoDealsEffect()

    data class PagingLoadingErrorEffect(
        val error: Throwable
    ) : IpoDealsEffect()

    data class PagingDealsLoadedEffect(
        val deals: List<Deal>
    ) : IpoDealsEffect()

}