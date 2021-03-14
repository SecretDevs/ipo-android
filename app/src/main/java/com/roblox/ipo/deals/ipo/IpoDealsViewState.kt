package com.roblox.ipo.deals.ipo

import com.roblox.ipo.base.MviViewState
import com.roblox.ipo.vo.inapp.Deal

data class IpoDealsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val deals: List<Deal>
) : MviViewState {
    override fun log(): String = this.toString()

    companion object {

        val initialState = IpoDealsViewState(
            isInitialLoading = false,
            initialError = null,
            deals = emptyList()
        )

        val initialLoadingState = IpoDealsViewState(
            isInitialLoading = true,
            initialError = null,
            deals = emptyList()
        )

        fun loadingErrorState(error: Throwable?) = IpoDealsViewState(
            isInitialLoading = false,
            initialError = error,
            deals = emptyList()
        )

        fun dealsLoadedState(deals: List<Deal>) = IpoDealsViewState(
            isInitialLoading = false,
            initialError = null,
            deals = deals
        )

    }

}