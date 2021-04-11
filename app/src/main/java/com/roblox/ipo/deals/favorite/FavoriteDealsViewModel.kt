package com.roblox.ipo.deals.favorite

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.vo.inapp.Result

class FavoriteDealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase
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
                intent.dealId,
                intent.newState
            )
            FavoriteDealsIntent.FavoriteDealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            FavoriteDealsIntent.PagingDealsLoadingIntent -> FavoriteDealsAction.PagingLoadDealsAction
            FavoriteDealsIntent.RetryPagingDealsLoadingIntent -> FavoriteDealsAction.PagingLoadDealsAction
        }

    override suspend fun performAction(action: FavoriteDealsAction): FavoriteDealsEffect =
        when (action) {
            FavoriteDealsAction.LoadDealsAction -> {
                addIntermediateEffect(FavoriteDealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getFavoriteDeals(null)) {
                    is Result.Error -> FavoriteDealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> FavoriteDealsEffect.DealsLoadedEffect(result.data)
                }
            }
            is FavoriteDealsAction.NavigateToDealDetails -> {
                FavoriteDealsEffect.NothingEffect
            }
            is FavoriteDealsAction.ToggleDealFaveAction -> {
                val result =
                    if (action.newState) dealsUseCase.addFave(action.dealId)
                    else dealsUseCase.removeFave(action.dealId)
                when (result) {
                    is Result.Error -> FavoriteDealsEffect.NothingEffect
                    is Result.Success -> FavoriteDealsEffect.NothingEffect
                }
            }
            FavoriteDealsAction.PagingLoadDealsAction -> {
                addIntermediateEffect(FavoriteDealsEffect.PagingLoadingEffect)
                when (val result =
                    dealsUseCase.getFavoriteDeals(viewStateLiveData.value!!.deals.last().updatedTime)) {
                    is Result.Error -> FavoriteDealsEffect.PagingLoadingErrorEffect(result.throwable)
                    is Result.Success -> FavoriteDealsEffect.PagingDealsLoadedEffect(result.data)
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
            is FavoriteDealsEffect.PagingDealsLoadedEffect -> FavoriteDealsViewState.dealsLoadedState(
                oldState.deals + effect.deals
            )
            FavoriteDealsEffect.PagingLoadingEffect -> FavoriteDealsViewState.dealsPagingLoadingState(
                oldState.deals
            )
            is FavoriteDealsEffect.PagingLoadingErrorEffect -> FavoriteDealsViewState.dealsPagingErrorLoadingState(
                oldState.deals,
                effect.error
            )
        }
}