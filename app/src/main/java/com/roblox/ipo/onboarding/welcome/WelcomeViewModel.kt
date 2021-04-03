package com.roblox.ipo.onboarding.welcome

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.navigation.Coordinator
import kotlinx.coroutines.delay

class WelcomeViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator
) : BaseViewModel<WelcomeViewState, WelcomeEffect, WelcomeIntent, WelcomeAction>() {
    override fun initialState(): WelcomeViewState = WelcomeViewState(isLogoShown = false)

    override fun intentInterpreter(intent: WelcomeIntent): WelcomeAction =
        when (intent) {
            WelcomeIntent.InitialIntent -> WelcomeAction.StartDelayedLogoAction
            WelcomeIntent.LoginClickIntent -> WelcomeAction.NavigateToLoginAction
            WelcomeIntent.WelcomeNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
        }

    override suspend fun performAction(action: WelcomeAction): WelcomeEffect =
        when (action) {
            WelcomeAction.NavigateToLoginAction -> {
                coordinator.navigateToLogin()
                WelcomeEffect.NothingEffect
            }
            WelcomeAction.StartDelayedLogoAction -> {
                addIntermediateEffect(WelcomeEffect.HideLogoEffect)
                delay(1000L)
                WelcomeEffect.ShowLogoEffect
            }
        }

    override fun stateReducer(oldState: WelcomeViewState, effect: WelcomeEffect): WelcomeViewState =
        when (effect) {
            WelcomeEffect.HideLogoEffect -> WelcomeViewState(isLogoShown = false)
            WelcomeEffect.NothingEffect -> oldState
            WelcomeEffect.ShowLogoEffect -> WelcomeViewState(isLogoShown = true)
        }

}