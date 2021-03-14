package com.roblox.ipo.deals.favorite

import com.roblox.ipo.base.MviViewState
import com.roblox.ipo.vo.inapp.Deal

data class FavoriteDealsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val deals: List<Deal>
) : MviViewState {
    override fun log(): String = this.toString()

    companion object {

        val initialState = FavoriteDealsViewState(
            isInitialLoading = false,
            initialError = null,
            deals = emptyList()
        )

        val initialLoadingState = FavoriteDealsViewState(
            isInitialLoading = true,
            initialError = null,
            deals = emptyList()
        )

        fun loadingErrorState(error: Throwable?) = FavoriteDealsViewState(
            isInitialLoading = false,
            initialError = error,
            deals = emptyList()
        )

        fun dealsLoadedState(deals: List<Deal>) = FavoriteDealsViewState(
            isInitialLoading = false,
            initialError = null,
            deals = deals
        )

    }

}