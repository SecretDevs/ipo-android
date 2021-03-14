package com.roblox.ipo.deals.spac

import com.roblox.ipo.base.MviViewState
import com.roblox.ipo.vo.inapp.Deal

data class SpacDealsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val deals: List<Deal>
) : MviViewState {
    override fun log(): String = this.toString()

    companion object {

        val initialState = SpacDealsViewState(
            isInitialLoading = false,
            initialError = null,
            deals = emptyList()
        )

        val initialLoadingState = SpacDealsViewState(
            isInitialLoading = true,
            initialError = null,
            deals = emptyList()
        )

        fun loadingErrorState(error: Throwable?) = SpacDealsViewState(
            isInitialLoading = false,
            initialError = error,
            deals = emptyList()
        )

        fun dealsLoadedState(deals: List<Deal>) = SpacDealsViewState(
            isInitialLoading = false,
            initialError = null,
            deals = deals
        )

    }

}