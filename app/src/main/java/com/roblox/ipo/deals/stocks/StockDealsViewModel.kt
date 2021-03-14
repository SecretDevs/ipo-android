package com.roblox.ipo.deals.stocks

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result

class StockDealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase,
    private val coordinator: Coordinator
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
                intent.dealId
            )
            StockDealsIntent.FavoriteDealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
        }

    override suspend fun performAction(action: StockDealsAction): StockDealsEffect =
        when (action) {
            StockDealsAction.LoadDealsAction -> {
                addIntermediateEffect(StockDealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getStocksDeals()) {
                    is Result.Error -> StockDealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> StockDealsEffect.DealsLoadedEffect(result.data)
                }
            }
            is StockDealsAction.NavigateToDealDetails -> {
                StockDealsEffect.NothingEffect
            }
            is StockDealsAction.ToggleDealFaveAction -> {
                when (val result = dealsUseCase.updateFave(action.dealId)) {
                    is Result.Error -> StockDealsEffect.NothingEffect
                    is Result.Success -> StockDealsEffect.NothingEffect
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
        }
}