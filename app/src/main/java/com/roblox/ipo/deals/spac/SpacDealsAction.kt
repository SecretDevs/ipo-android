package com.roblox.ipo.deals.spac

import com.roblox.ipo.base.MviAction

sealed class SpacDealsAction : MviAction {

    object LoadDealsAction : SpacDealsAction()

    data class NavigateToDealDetails(
        val dealId: Long
    ) : SpacDealsAction()

    data class ToggleDealFaveAction(
        val dealId: Long
    ) : SpacDealsAction()

}