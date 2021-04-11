package com.roblox.ipo.onboarding.quiz.risk

import com.roblox.ipo.base.MviAction

sealed class QuizRiskAction : MviAction {

    data class CheckSelectedAction(
        val id: Int
    ) : QuizRiskAction()

    object SaveQuizAction : QuizRiskAction()

    object SkipQuizClickAction : QuizRiskAction()

    object NavigateBackAction : QuizRiskAction()

}