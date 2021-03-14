package com.roblox.ipo.deals.spac

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result

class SpacDealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase,
    private val coordinator: Coordinator
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
                intent.dealId
            )
            SpacDealsIntent.FavoriteDealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
        }

    override suspend fun performAction(action: SpacDealsAction): SpacDealsEffect =
        when (action) {
            SpacDealsAction.LoadDealsAction -> {
                addIntermediateEffect(SpacDealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getSpacDeals()) {
                    is Result.Error -> SpacDealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> SpacDealsEffect.DealsLoadedEffect(result.data)
                }
            }
            is SpacDealsAction.NavigateToDealDetails -> {
                SpacDealsEffect.NothingEffect
            }
            is SpacDealsAction.ToggleDealFaveAction -> {
                when (val result = dealsUseCase.updateFave(action.dealId)) {
                    is Result.Error -> SpacDealsEffect.NothingEffect
                    is Result.Success -> SpacDealsEffect.NothingEffect
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
        }
}