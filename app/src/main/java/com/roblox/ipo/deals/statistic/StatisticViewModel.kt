package com.roblox.ipo.deals.statistic

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.StatisticUseCase
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result
import kotlinx.coroutines.delay

class StatisticViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator,
    private val statisticUseCase: StatisticUseCase
) : BaseViewModel<StatisticViewState, StatisticEffect, StatisticIntent, StatisticAction>() {
    override fun initialState(): StatisticViewState = StatisticViewState(
        isLoading = false,
        errorLoading = null,
        data = null,
        currentChartRange = StatisticChartRange.DAY
    )

    override fun intentInterpreter(intent: StatisticIntent): StatisticAction =
        when (intent) {
            StatisticIntent.BackArrowClickIntent -> StatisticAction.NavigateBackAction
            is StatisticIntent.ChangeChartRangeIntent -> StatisticAction.ChangeChartRangeAction(intent.range)
            StatisticIntent.InitialIntent -> StatisticAction.LoadStatisticAction
            StatisticIntent.StatisticNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            StatisticIntent.ReloadIntent -> StatisticAction.LoadStatisticAction
        }

    override suspend fun performAction(action: StatisticAction): StatisticEffect =
        when (action) {
            is StatisticAction.ChangeChartRangeAction -> StatisticEffect.ChangedStatisticRangeEffect(
                action.range
            )
            StatisticAction.LoadStatisticAction -> {
                addIntermediateEffect(StatisticEffect.LoadingEffect)
                delay(1000)
                when (val result = statisticUseCase.getPortfolio()) {
                    is Result.Error -> StatisticEffect.LoadingErrorEffect(result.throwable)
                    is Result.Success -> StatisticEffect.LoadedDataEffect(result.data)
                }
            }
            StatisticAction.NavigateBackAction -> {
                coordinator.pop()
                StatisticEffect.NothingEffect
            }
        }

    override fun stateReducer(
        oldState: StatisticViewState,
        effect: StatisticEffect
    ): StatisticViewState =
        when (effect) {
            is StatisticEffect.ChangedStatisticRangeEffect -> StatisticViewState(
                isLoading = oldState.isLoading,
                errorLoading = oldState.errorLoading,
                data = oldState.data,
                currentChartRange = effect.range
            )
            is StatisticEffect.LoadedDataEffect -> StatisticViewState(
                isLoading = false,
                errorLoading = null,
                data = effect.data,
                currentChartRange = oldState.currentChartRange
            )
            StatisticEffect.LoadingEffect -> StatisticViewState(
                isLoading = true,
                errorLoading = null,
                data = null,
                currentChartRange = oldState.currentChartRange
            )
            is StatisticEffect.LoadingErrorEffect -> StatisticViewState(
                isLoading = false,
                errorLoading = effect.error,
                data = null,
                currentChartRange = oldState.currentChartRange
            )
            StatisticEffect.NothingEffect -> oldState
        }

}