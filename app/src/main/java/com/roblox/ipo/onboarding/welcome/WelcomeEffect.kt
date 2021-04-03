package com.roblox.ipo.onboarding.welcome

import com.roblox.ipo.base.MviEffect

sealed class WelcomeEffect : MviEffect {

    object HideLogoEffect : WelcomeEffect()

    object ShowLogoEffect : WelcomeEffect()

    object NothingEffect : WelcomeEffect()

}