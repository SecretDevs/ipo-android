package com.roblox.ipo.deals

import com.roblox.ipo.base.MviViewState

data class DealsViewState(
    val isInitialLoading: Boolean = false,
    val initialLoadingError: Throwable? = null,
    val states: List<Boolean> = emptyList()
) : MviViewState {
    override fun log(): String = this.toString()
}