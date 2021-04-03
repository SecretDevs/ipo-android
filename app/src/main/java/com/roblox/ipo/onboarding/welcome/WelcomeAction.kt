package com.roblox.ipo.onboarding.welcome

import com.roblox.ipo.base.MviAction

sealed class WelcomeAction : MviAction {

    object StartDelayedLogoAction : WelcomeAction()

    object NavigateToLoginAction : WelcomeAction()

}