package com.roblox.ipo.deals.stocks

import com.roblox.ipo.base.MviAction

sealed class StockDealsAction : MviAction {

    object LoadDealsAction : StockDealsAction()

    data class NavigateToDealDetails(
        val dealId: Long
    ) : StockDealsAction()

    data class ToggleDealFaveAction(
        val dealId: Long
    ) : StockDealsAction()

}