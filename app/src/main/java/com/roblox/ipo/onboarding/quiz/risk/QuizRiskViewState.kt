package com.roblox.ipo.onboarding.quiz.risk

import com.roblox.ipo.base.MviViewState

data class QuizRiskViewState(
    val selectedId: Int,
    val isLoading: Boolean,
    val isError: Throwable?
) : MviViewState {
    override fun log(): String = this.toString()
}