package com.roblox.ipo.deals.favorite

import com.roblox.ipo.base.MviAction

sealed class FavoriteDealsAction : MviAction {

    object LoadDealsAction : FavoriteDealsAction()

    object PagingLoadDealsAction : FavoriteDealsAction()

    data class NavigateToDealDetails(
        val dealId: String
    ) : FavoriteDealsAction()

    data class ToggleDealFaveAction(
        val dealId: String,
        val newState: Boolean
    ) : FavoriteDealsAction()

}