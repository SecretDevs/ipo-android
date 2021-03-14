package com.roblox.ipo.deals.ipo

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result

class IpoDealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase,
    private val coordinator: Coordinator
) : BaseViewModel<IpoDealsViewState, IpoDealsEffect, IpoDealsIntent, IpoDealsAction>() {
    override fun initialState(): IpoDealsViewState = IpoDealsViewState.initialState

    override fun intentInterpreter(intent: IpoDealsIntent): IpoDealsAction =
        when (intent) {
            IpoDealsIntent.InitialDealsLoadingIntent -> IpoDealsAction.LoadDealsAction
            is IpoDealsIntent.OpenDealDetailsIntent -> IpoDealsAction.NavigateToDealDetails(
                intent.dealId
            )
            IpoDealsIntent.RetryDealsLoadingIntent -> IpoDealsAction.LoadDealsAction
            is IpoDealsIntent.ToggleDealFaveIntent -> IpoDealsAction.ToggleDealFaveAction(
                intent.dealId
            )
            IpoDealsIntent.FavoriteDealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
        }

    override suspend fun performAction(action: IpoDealsAction): IpoDealsEffect =
        when (action) {
            IpoDealsAction.LoadDealsAction -> {
                addIntermediateEffect(IpoDealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getIpoDeals()) {
                    is Result.Error -> IpoDealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> IpoDealsEffect.DealsLoadedEffect(result.data)
                }
            }
            is IpoDealsAction.NavigateToDealDetails -> {
                IpoDealsEffect.NohtingEffect
            }
            is IpoDealsAction.ToggleDealFaveAction -> {
                when (val result = dealsUseCase.updateFave(action.dealId)) {
                    is Result.Error -> IpoDealsEffect.NohtingEffect
                    is Result.Success -> IpoDealsEffect.NohtingEffect
                }
            }
        }

    override fun stateReducer(
        oldState: IpoDealsViewState,
        effect: IpoDealsEffect
    ): IpoDealsViewState =
        when (effect) {
            is IpoDealsEffect.DealsLoadedEffect -> IpoDealsViewState.dealsLoadedState(
                effect.deals
            )
            IpoDealsEffect.InitialLoadingEffect -> IpoDealsViewState.initialLoadingState
            is IpoDealsEffect.InitialLoadingErrorEffect -> IpoDealsViewState.loadingErrorState(
                effect.error
            )
            IpoDealsEffect.NohtingEffect -> oldState
        }
}