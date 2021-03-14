package com.roblox.ipo.deals.stocks

import com.roblox.ipo.base.MviEffect
import com.roblox.ipo.vo.inapp.Deal

sealed class StockDealsEffect : MviEffect {

    object InitialLoadingEffect : StockDealsEffect()

    data class InitialLoadingErrorEffect(
        val error: Throwable?
    ) : StockDealsEffect()

    data class DealsLoadedEffect(
        val deals: List<Deal>
    ) : StockDealsEffect()

    object NothingEffect : StockDealsEffect()

}