package com.roblox.ipo.deals.stocks

import com.roblox.ipo.base.MviViewState
import com.roblox.ipo.vo.inapp.Deal

data class StockDealsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val isPagingLoading: Boolean,
    val pagingError: Throwable?,
    val deals: List<Deal>
) : MviViewState {
    override fun log(): String = this.toString()

    companion object {

        val initialState = StockDealsViewState(
            isInitialLoading = false,
            initialError = null,
            isPagingLoading = false,
            pagingError = null,
            deals = emptyList()
        )

        val initialLoadingState = StockDealsViewState(
            isInitialLoading = true,
            initialError = null,
            isPagingLoading = false,
            pagingError = null,
            deals = emptyList()
        )

        fun loadingErrorState(error: Throwable?) = StockDealsViewState(
            isInitialLoading = false,
            initialError = error,
            isPagingLoading = false,
            pagingError = null,
            deals = emptyList()
        )

        fun dealsLoadedState(deals: List<Deal>) = StockDealsViewState(
            isInitialLoading = false,
            initialError = null,
            isPagingLoading = false,
            pagingError = null,
            deals = deals
        )

        fun dealsPagingLoadingState(deals: List<Deal>) = StockDealsViewState(
            isInitialLoading = false,
            initialError = null,
            isPagingLoading = true,
            pagingError = null,
            deals = deals
        )

        fun dealsPagingErrorLoadingState(
            deals: List<Deal>,
            pagingError: Throwable
        ) = StockDealsViewState(
            isInitialLoading = false,
            initialError = null,
            isPagingLoading = false,
            pagingError = pagingError,
            deals = deals
        )

    }

}