package com.roblox.ipo.onboarding.welcome

import com.roblox.ipo.base.MviViewState

data class WelcomeViewState(
    val isLogoShown: Boolean
) : MviViewState {
    override fun log(): String = this.toString()
}