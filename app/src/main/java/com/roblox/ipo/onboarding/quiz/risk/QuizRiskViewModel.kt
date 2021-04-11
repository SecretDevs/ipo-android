package com.roblox.ipo.onboarding.quiz.risk

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.QuizUseCase
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result

class QuizRiskViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator,
    private val quizUseCase: QuizUseCase
) : BaseViewModel<QuizRiskViewState, QuizRiskEffect, QuizRiskIntent, QuizRiskAction>() {
    override fun initialState(): QuizRiskViewState = QuizRiskViewState(
        selectedId = -1,
        isLoading = false,
        isError = null
    )

    override fun intentInterpreter(intent: QuizRiskIntent): QuizRiskAction =
        when (intent) {
            QuizRiskIntent.BackArrowClickIntent -> QuizRiskAction.NavigateBackAction
            is QuizRiskIntent.CheckSelectedIntent -> QuizRiskAction.CheckSelectedAction(intent.id)
            QuizRiskIntent.InitialIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            QuizRiskIntent.NextButtonClickIntent -> QuizRiskAction.SaveQuizAction
            QuizRiskIntent.QuizRiskNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            QuizRiskIntent.SkipQuizClickIntent -> QuizRiskAction.SkipQuizClickAction
        }

    override suspend fun performAction(action: QuizRiskAction): QuizRiskEffect =
        when (action) {
            is QuizRiskAction.CheckSelectedAction -> QuizRiskEffect.ChangeSelectedEffect(action.id)
            QuizRiskAction.NavigateBackAction -> {
                quizUseCase.clear()
                coordinator.navigateToDeals()
                QuizRiskEffect.NothingEffect
            }
            QuizRiskAction.SaveQuizAction -> {
                quizUseCase.saveRisk(
                    idToSavingMapping[
                            viewStateLiveData.value!!.selectedId
                    ] ?: 0
                )
                addIntermediateEffect(QuizRiskEffect.LoadingQuizResultsEffect)
                when (val result = quizUseCase.sendQuiz()) {
                    is Result.Error -> QuizRiskEffect.SavingQuizErrorEffect(result.throwable)
                    is Result.Success ->
                        if (result.data) {
                            coordinator.navigateToDeals()
                            QuizRiskEffect.NothingEffect
                        } else QuizRiskEffect.SavingQuizErrorEffect(Throwable("problems"))
                }
            }
            QuizRiskAction.SkipQuizClickAction -> TODO()
        }

    override fun stateReducer(
        oldState: QuizRiskViewState,
        effect: QuizRiskEffect
    ): QuizRiskViewState =
        when (effect) {
            is QuizRiskEffect.ChangeSelectedEffect -> QuizRiskViewState(
                selectedId = effect.id,
                isLoading = false,
                isError = null
            )
            QuizRiskEffect.NothingEffect -> oldState
            is QuizRiskEffect.SavingQuizErrorEffect -> QuizRiskViewState(
                selectedId = oldState.selectedId,
                isLoading = false,
                isError = effect.throwable
            )
            QuizRiskEffect.LoadingQuizResultsEffect -> QuizRiskViewState(
                selectedId = oldState.selectedId,
                isLoading = true,
                isError = null
            )
        }

    companion object {
        private val idToSavingMapping = mapOf(
            R.id.quiz_card_risk_1 to 0,
            R.id.quiz_card_risk_2 to 1,
            R.id.quiz_card_risk_5 to 2,
            R.id.quiz_card_risk_6 to 3
        )
    }

}