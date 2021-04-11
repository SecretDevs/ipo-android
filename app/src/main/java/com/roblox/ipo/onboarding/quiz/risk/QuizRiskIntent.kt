package com.roblox.ipo.onboarding.quiz.risk

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class QuizRiskIntent : MviIntent {

    object InitialIntent : QuizRiskIntent(), NothingIntent

    data class CheckSelectedIntent(
        val id: Int
    ) : QuizRiskIntent()

    object NextButtonClickIntent : QuizRiskIntent()

    object SkipQuizClickIntent : QuizRiskIntent()

    object BackArrowClickIntent : QuizRiskIntent()

    object QuizRiskNothingIntent : QuizRiskIntent(), NothingIntent

}