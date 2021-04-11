package com.roblox.ipo.deals.ipo

import com.roblox.ipo.base.MviAction

sealed class IpoDealsAction : MviAction {

    object LoadDealsAction : IpoDealsAction()

    object PagingLoadDealsAction : IpoDealsAction()

    data class NavigateToDealDetails(
        val dealId: String
    ) : IpoDealsAction()

    data class ToggleDealFaveAction(
        val dealId: String,
        val newState: Boolean
    ) : IpoDealsAction()

}