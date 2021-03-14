package com.roblox.ipo.deals.ipo

import com.roblox.ipo.base.MviAction

sealed class IpoDealsAction : MviAction {

    object LoadDealsAction : IpoDealsAction()

    data class NavigateToDealDetails(
        val dealId: Long
    ) : IpoDealsAction()

    data class ToggleDealFaveAction(
        val dealId: Long
    ) : IpoDealsAction()

}