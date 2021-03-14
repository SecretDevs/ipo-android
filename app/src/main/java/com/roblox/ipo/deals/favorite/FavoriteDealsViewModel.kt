package com.roblox.ipo.deals.favorite

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result

class FavoriteDealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase,
    private val coordinator: Coordinator
) : BaseViewModel<FavoriteDealsViewState, FavoriteDealsEffect, FavoriteDealsIntent, FavoriteDealsAction>() {
    override fun initialState(): FavoriteDealsViewState = FavoriteDealsViewState.initialState

    override fun intentInterpreter(intent: FavoriteDealsIntent): FavoriteDealsAction =
        when (intent) {
            FavoriteDealsIntent.InitialDealsLoadingIntent -> FavoriteDealsAction.LoadDealsAction
            is FavoriteDealsIntent.OpenDealDetailsIntent -> FavoriteDealsAction.NavigateToDealDetails(
                intent.dealId
            )
            FavoriteDealsIntent.RetryDealsLoadingIntent -> FavoriteDealsAction.LoadDealsAction
            is FavoriteDealsIntent.ToggleDealFaveIntent -> FavoriteDealsAction.ToggleDealFaveAction(
                intent.dealId
            )
            FavoriteDealsIntent.FavoriteDealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
        }

    override suspend fun performAction(action: FavoriteDealsAction): FavoriteDealsEffect =
        when (action) {
            FavoriteDealsAction.LoadDealsAction -> {
                addIntermediateEffect(FavoriteDealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getFavoriteDeals()) {
                    is Result.Error -> FavoriteDealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> FavoriteDealsEffect.DealsLoadedEffect(result.data)
                }
            }
            is FavoriteDealsAction.NavigateToDealDetails -> {
                FavoriteDealsEffect.NothingEffect
            }
            is FavoriteDealsAction.ToggleDealFaveAction -> {
                when (val result = dealsUseCase.updateFave(action.dealId)) {
                    is Result.Error -> FavoriteDealsEffect.NothingEffect
                    is Result.Success -> FavoriteDealsEffect.NothingEffect
                }
            }
        }

    override fun stateReducer(
        oldState: FavoriteDealsViewState,
        effect: FavoriteDealsEffect
    ): FavoriteDealsViewState =
        when (effect) {
            is FavoriteDealsEffect.DealsLoadedEffect -> FavoriteDealsViewState.dealsLoadedState(
                effect.deals
            )
            FavoriteDealsEffect.InitialLoadingEffect -> FavoriteDealsViewState.initialLoadingState
            is FavoriteDealsEffect.InitialLoadingErrorEffect -> FavoriteDealsViewState.loadingErrorState(
                effect.error
            )
            FavoriteDealsEffect.NothingEffect -> oldState
        }
}