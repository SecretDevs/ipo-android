package com.roblox.ipo.deals.stocks

import com.roblox.ipo.base.MviViewState
import com.roblox.ipo.vo.inapp.Deal

data class StockDealsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val deals: List<Deal>
) : MviViewState {
    override fun log(): String = this.toString()

    companion object {

        val initialState = StockDealsViewState(
            isInitialLoading = false,
            initialError = null,
            deals = emptyList()
        )

        val initialLoadingState = StockDealsViewState(
            isInitialLoading = true,
            initialError = null,
            deals = emptyList()
        )

        fun loadingErrorState(error: Throwable?) = StockDealsViewState(
            isInitialLoading = false,
            initialError = error,
            deals = emptyList()
        )

        fun dealsLoadedState(deals: List<Deal>) = StockDealsViewState(
            isInitialLoading = false,
            initialError = null,
            deals = deals
        )

    }

}