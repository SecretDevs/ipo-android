package com.roblox.ipo.deals.spac

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.vo.inapp.Result

class SpacDealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase
) : BaseViewModel<SpacDealsViewState, SpacDealsEffect, SpacDealsIntent, SpacDealsAction>() {
    override fun initialState(): SpacDealsViewState = SpacDealsViewState.initialState

    override fun intentInterpreter(intent: SpacDealsIntent): SpacDealsAction =
        when (intent) {
            SpacDealsIntent.InitialDealsLoadingIntent -> SpacDealsAction.LoadDealsAction
            is SpacDealsIntent.OpenDealDetailsIntent -> SpacDealsAction.NavigateToDealDetails(
                intent.dealId
            )
            SpacDealsIntent.RetryDealsLoadingIntent -> SpacDealsAction.LoadDealsAction
            is SpacDealsIntent.ToggleDealFaveIntent -> SpacDealsAction.ToggleDealFaveAction(
                intent.dealId,
                intent.newState
            )
            SpacDealsIntent.FavoriteDealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            SpacDealsIntent.PagingDealsLoadingIntent -> SpacDealsAction.PagingLoadDealsAction
            SpacDealsIntent.RetryPagingDealsLoadingIntent -> SpacDealsAction.PagingLoadDealsAction
        }

    override suspend fun performAction(action: SpacDealsAction): SpacDealsEffect =
        when (action) {
            SpacDealsAction.LoadDealsAction -> {
                addIntermediateEffect(SpacDealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getSpacDeals(null)) {
                    is Result.Error -> SpacDealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> SpacDealsEffect.DealsLoadedEffect(result.data)
                }
            }
            is SpacDealsAction.NavigateToDealDetails -> {
                SpacDealsEffect.NothingEffect
            }
            is SpacDealsAction.ToggleDealFaveAction -> {
                val result =
                    if (action.newState) dealsUseCase.addFave(action.dealId)
                    else dealsUseCase.removeFave(action.dealId)
                when (result) {
                    is Result.Error -> SpacDealsEffect.NothingEffect
                    is Result.Success -> SpacDealsEffect.NothingEffect
                }
            }
            SpacDealsAction.PagingLoadDealsAction -> {
                addIntermediateEffect(SpacDealsEffect.PagingLoadingEffect)
                when (val result =
                    dealsUseCase.getSpacDeals(viewStateLiveData.value!!.deals.last().updatedTime)) {
                    is Result.Error -> SpacDealsEffect.PagingLoadingErrorEffect(result.throwable)
                    is Result.Success -> SpacDealsEffect.PagingDealsLoadedEffect(result.data)
                }
            }
        }

    override fun stateReducer(
        oldState: SpacDealsViewState,
        effect: SpacDealsEffect
    ): SpacDealsViewState =
        when (effect) {
            is SpacDealsEffect.DealsLoadedEffect -> SpacDealsViewState.dealsLoadedState(
                effect.deals
            )
            SpacDealsEffect.InitialLoadingEffect -> SpacDealsViewState.initialLoadingState
            is SpacDealsEffect.InitialLoadingErrorEffect -> SpacDealsViewState.loadingErrorState(
                effect.error
            )
            SpacDealsEffect.NothingEffect -> oldState
            is SpacDealsEffect.PagingDealsLoadedEffect -> SpacDealsViewState.dealsLoadedState(
                oldState.deals + effect.deals
            )
            SpacDealsEffect.PagingLoadingEffect -> SpacDealsViewState.dealsPagingLoadingState(
                oldState.deals
            )
            is SpacDealsEffect.PagingLoadingErrorEffect -> SpacDealsViewState.dealsPagingErrorLoadingState(
                oldState.deals,
                effect.error
            )
        }
}