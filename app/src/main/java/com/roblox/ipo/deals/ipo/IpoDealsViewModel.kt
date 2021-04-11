package com.roblox.ipo.deals.ipo

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.DealsUseCase
import com.roblox.ipo.vo.inapp.Result

class IpoDealsViewModel @ViewModelInject constructor(
    private val dealsUseCase: DealsUseCase
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
                intent.dealId,
                intent.newState
            )
            IpoDealsIntent.FavoriteDealsNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            IpoDealsIntent.PagingDealsLoadingIntent -> IpoDealsAction.PagingLoadDealsAction
            IpoDealsIntent.RetryPagingDealsLoadingIntent -> IpoDealsAction.PagingLoadDealsAction
        }

    override suspend fun performAction(action: IpoDealsAction): IpoDealsEffect =
        when (action) {
            IpoDealsAction.LoadDealsAction -> {
                addIntermediateEffect(IpoDealsEffect.InitialLoadingEffect)
                when (val result = dealsUseCase.getIpoDeals(null)) {
                    is Result.Error -> IpoDealsEffect.InitialLoadingErrorEffect(result.throwable)
                    is Result.Success -> IpoDealsEffect.DealsLoadedEffect(result.data)
                }
            }
            is IpoDealsAction.NavigateToDealDetails -> {
                IpoDealsEffect.NothingEffect
            }
            is IpoDealsAction.ToggleDealFaveAction -> {
                val result =
                    if (action.newState) dealsUseCase.addFave(action.dealId)
                    else dealsUseCase.removeFave(action.dealId)
                when (result) {
                    is Result.Error -> IpoDealsEffect.NothingEffect
                    is Result.Success -> IpoDealsEffect.NothingEffect
                }
            }
            IpoDealsAction.PagingLoadDealsAction -> {
                addIntermediateEffect(IpoDealsEffect.PagingLoadingEffect)
                when (val result =
                    dealsUseCase.getIpoDeals(viewStateLiveData.value!!.deals.last().updatedTime)) {
                    is Result.Error -> IpoDealsEffect.PagingLoadingErrorEffect(result.throwable)
                    is Result.Success -> IpoDealsEffect.PagingDealsLoadedEffect(result.data)
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
            IpoDealsEffect.NothingEffect -> oldState
            is IpoDealsEffect.PagingDealsLoadedEffect -> IpoDealsViewState.dealsLoadedState(
                oldState.deals + effect.deals
            )
            IpoDealsEffect.PagingLoadingEffect -> IpoDealsViewState.dealsPagingLoadingState(
                oldState.deals
            )
            is IpoDealsEffect.PagingLoadingErrorEffect -> IpoDealsViewState.dealsPagingErrorLoadingState(
                oldState.deals,
                effect.error
            )
        }
}