package com.roblox.ipo.deals.stocks

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.deals.spac.SpacDealsEffect
import com.roblox.ipo.deals.spac.SpacDealsViewState
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result

class StockDealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase
) : BaseViewModel<StockDealsViewState, StockDealsEffect, StockDealsIntent, StockDealsAction>() {
    override fun initialState(): StockDealsViewState = StockDealsViewState.initialState

    override fun intentInterpreter(intent: StockDealsIntent): StockDealsAction =
        when (intent) {
            StockDealsIntent.InitialDealsLoadingIntent -> StockDealsAction.LoadDealsAction
            is StockDealsIntent.OpenDealDetailsIntent -> StockDealsAction.NavigateToDealDetails(
                intent.dealId
            )
            StockDealsIntent.RetryDealsLoadingIntent -> StockDealsAction.LoadDealsAction
            is StockDealsIntent.ToggleDealFaveIntent -> StockDealsAction.ToggleDealFaveAction(
                intent.dealId,
                intent.newState
            )
            StockDealsIntent.FavoriteDealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            StockDealsIntent.PagingDealsLoadingIntent -> StockDealsAction.PagingLoadDealsAction
            StockDealsIntent.RetryPagingDealsLoadingIntent -> StockDealsAction.PagingLoadDealsAction
        }

    override suspend fun performAction(action: StockDealsAction): StockDealsEffect =
        when (action) {
            StockDealsAction.LoadDealsAction -> {
                addIntermediateEffect(StockDealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getStocksDeals(null)) {
                    is Result.Error -> StockDealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> StockDealsEffect.DealsLoadedEffect(result.data)
                }
            }
            is StockDealsAction.NavigateToDealDetails -> {
                StockDealsEffect.NothingEffect
            }
            is StockDealsAction.ToggleDealFaveAction -> {
                val result =
                    if (action.newState) dealsUseCase.addFave(action.dealId)
                    else dealsUseCase.removeFave(action.dealId)
                when (result) {
                    is Result.Error -> StockDealsEffect.NothingEffect
                    is Result.Success -> StockDealsEffect.NothingEffect
                }
            }
            StockDealsAction.PagingLoadDealsAction -> {
                addIntermediateEffect(StockDealsEffect.PagingLoadingEffect)
                when (val result =
                    dealsUseCase.getStocksDeals(viewStateLiveData.value!!.deals.last().updatedTime)) {
                    is Result.Error -> StockDealsEffect.PagingLoadingErrorEffect(result.throwable)
                    is Result.Success -> StockDealsEffect.PagingDealsLoadedEffect(result.data)
                }
            }
        }

    override fun stateReducer(
        oldState: StockDealsViewState,
        effect: StockDealsEffect
    ): StockDealsViewState =
        when (effect) {
            is StockDealsEffect.DealsLoadedEffect -> StockDealsViewState.dealsLoadedState(
                effect.deals
            )
            StockDealsEffect.InitialLoadingEffect -> StockDealsViewState.initialLoadingState
            is StockDealsEffect.InitialLoadingErrorEffect -> StockDealsViewState.loadingErrorState(
                effect.error
            )
            StockDealsEffect.NothingEffect -> oldState
            is StockDealsEffect.PagingDealsLoadedEffect -> StockDealsViewState.dealsLoadedState(
                oldState.deals + effect.deals
            )
            StockDealsEffect.PagingLoadingEffect -> StockDealsViewState.dealsPagingLoadingState(
                oldState.deals
            )
            is StockDealsEffect.PagingLoadingErrorEffect -> StockDealsViewState.dealsPagingErrorLoadingState(
                oldState.deals,
                effect.error
            )
        }
}