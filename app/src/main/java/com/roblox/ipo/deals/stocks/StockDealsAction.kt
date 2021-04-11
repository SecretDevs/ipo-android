package com.roblox.ipo.deals.stocks

import com.roblox.ipo.base.MviAction

sealed class StockDealsAction : MviAction {

    object LoadDealsAction : StockDealsAction()

    object PagingLoadDealsAction : StockDealsAction()

    data class NavigateToDealDetails(
        val dealId: String
    ) : StockDealsAction()

    data class ToggleDealFaveAction(
        val dealId: String,
        val newState: Boolean
    ) : StockDealsAction()

}