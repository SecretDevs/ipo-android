package com.roblox.ipo.deals.spac

import com.roblox.ipo.base.MviAction

sealed class SpacDealsAction : MviAction {

    object LoadDealsAction : SpacDealsAction()

    object PagingLoadDealsAction : SpacDealsAction()

    data class NavigateToDealDetails(
        val dealId: String
    ) : SpacDealsAction()

    data class ToggleDealFaveAction(
        val dealId: String,
        val newState: Boolean
    ) : SpacDealsAction()

}