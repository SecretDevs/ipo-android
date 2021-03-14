package com.roblox.ipo.deals.favorite

import com.roblox.ipo.base.MviAction

sealed class FavoriteDealsAction : MviAction {

    object LoadDealsAction : FavoriteDealsAction()

    data class NavigateToDealDetails(
        val dealId: Long
    ) : FavoriteDealsAction()

    data class ToggleDealFaveAction(
        val dealId: Long
    ) : FavoriteDealsAction()

}