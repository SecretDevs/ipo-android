package com.roblox.ipo.onboarding.quiz.risk

import com.roblox.ipo.base.MviEffect

sealed class QuizRiskEffect : MviEffect {

    object LoadingQuizResultsEffect : QuizRiskEffect()

    data class ChangeSelectedEffect(
        val id: Int
    ) : QuizRiskEffect()

    object NothingEffect : QuizRiskEffect()

    data class SavingQuizErrorEffect(
        val throwable: Throwable
    ) : QuizRiskEffect()

}