package com.roblox.ipo.deals

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result

class DealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase,
    private val coordinator: Coordinator
) : BaseViewModel<DealsViewState, DealsEffect, DealsIntent, DealsAction>() {
    override fun initialState(): DealsViewState = DealsViewState()

    override fun intentInterpreter(intent: DealsIntent): DealsAction =
        when (intent) {
            DealsIntent.LoadDealsOpenStatesIntent -> DealsAction.LoadDealsOpenStatesAction
            DealsIntent.DealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            DealsIntent.OpenDealsStatisticIntent -> DealsAction.NavigateToDealsStatisticAction
            DealsIntent.ReloadDealsOpenStatesIntent -> DealsAction.LoadDealsOpenStatesAction
        }

    override suspend fun performAction(action: DealsAction): DealsEffect =
        when (action) {
            DealsAction.LoadDealsOpenStatesAction -> {
                addIntermediateEffect(DealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getDealsStates()) {
                    is Result.Error -> DealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> DealsEffect.DealsStatesLoadedEffect(result.data)
                }
            }
            DealsAction.NavigateToDealsStatisticAction -> {
                DealsEffect.NothingEffect
            }
        }

    override fun stateReducer(oldState: DealsViewState, effect: DealsEffect): DealsViewState =
        when (effect) {
            is DealsEffect.DealsStatesLoadedEffect -> DealsViewState(states = effect.states)
            DealsEffect.InitialLoadingEffect -> DealsViewState(isInitialLoading = true)
            is DealsEffect.InitialLoadingErrorEffect -> DealsViewState(initialLoadingError = effect.throwable)
            DealsEffect.NothingEffect -> oldState
        }
}