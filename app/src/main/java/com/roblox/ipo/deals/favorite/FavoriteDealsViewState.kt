package com.roblox.ipo.deals.favorite

import com.roblox.ipo.base.MviViewState
import com.roblox.ipo.vo.inapp.Deal

data class FavoriteDealsViewState(
    val isInitialLoading: Boolean,
    val initialError: Throwable?,
    val isPagingLoading: Boolean,
    val pagingError: Throwable?,
    val deals: List<Deal>
) : MviViewState {
    override fun log(): String = this.toString()

    companion object {

        val initialState = FavoriteDealsViewState(
            isInitialLoading = false,
            initialError = null,
            isPagingLoading = false,
            pagingError = null,
            deals = emptyList()
        )

        val initialLoadingState = FavoriteDealsViewState(
            isInitialLoading = true,
            initialError = null,
            isPagingLoading = false,
            pagingError = null,
            deals = emptyList()
        )

        fun loadingErrorState(error: Throwable?) = FavoriteDealsViewState(
            isInitialLoading = false,
            initialError = error,
            isPagingLoading = false,
            pagingError = null,
            deals = emptyList()
        )

        fun dealsLoadedState(deals: List<Deal>) = FavoriteDealsViewState(
            isInitialLoading = false,
            initialError = null,
            isPagingLoading = false,
            pagingError = null,
            deals = deals
        )

        fun dealsPagingLoadingState(deals: List<Deal>) = FavoriteDealsViewState(
            isInitialLoading = false,
            initialError = null,
            isPagingLoading = true,
            pagingError = null,
            deals = deals
        )

        fun dealsPagingErrorLoadingState(
            deals: List<Deal>,
            pagingError: Throwable
        ) = FavoriteDealsViewState(
            isInitialLoading = false,
            initialError = null,
            isPagingLoading = false,
            pagingError = pagingError,
            deals = deals
        )
    }

}