package com.roblox.ipo.onboarding.login

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.navigation.Coordinator

class LoginViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator
) : BaseViewModel<LoginViewState, LoginEffect, LoginIntent, LoginAction>() {
    override fun initialState(): LoginViewState = LoginViewState(
        errorCode = -1,
        isLengthFits = false,
        isTermsChecked = false
    )

    override fun intentInterpreter(intent: LoginIntent): LoginAction =
        when (intent) {
            LoginIntent.BackArrowClickIntent -> LoginAction.NavigateBackAction
            is LoginIntent.LoginClickIntent -> LoginAction.NavigateToConfirmationAction(intent.phone)
            LoginIntent.LoginNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            LoginIntent.InitialIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            is LoginIntent.PhoneLengthChangeIntent -> LoginAction.PhoneLengthChangeAction(
                intent.length
            )
            is LoginIntent.TermsAcceptStateChangeIntent -> LoginAction.TermsAcceptStateChangeAction(
                intent.isAccepted
            )
        }

    override suspend fun performAction(action: LoginAction): LoginEffect =
        when (action) {
            LoginAction.NavigateBackAction -> {
                coordinator.pop()
                LoginEffect.NothingEffect
            }
            is LoginAction.NavigateToConfirmationAction -> {
                coordinator.navigateToConfirmation()
                LoginEffect.NothingEffect
            }
            is LoginAction.PhoneLengthChangeAction -> LoginEffect.PhoneLengthChangeEffect(
                action.length == 10
            )
            is LoginAction.TermsAcceptStateChangeAction -> LoginEffect.TermsChangeEffect(
                action.isAccepted
            )
        }

    override fun stateReducer(
        oldState: LoginViewState,
        effect: LoginEffect
    ): LoginViewState =
        when (effect) {
            LoginEffect.NothingEffect -> oldState
            is LoginEffect.WrongPhoneEffect -> LoginViewState(
                errorCode = effect.errorCode,
                isLengthFits = oldState.isLengthFits,
                isTermsChecked = oldState.isTermsChecked
            )
            is LoginEffect.PhoneLengthChangeEffect -> LoginViewState(
                errorCode = -1,
                isLengthFits = effect.isLengthFits,
                isTermsChecked = oldState.isTermsChecked
            )
            is LoginEffect.TermsChangeEffect -> LoginViewState(
                errorCode = oldState.errorCode,
                isLengthFits = oldState.isLengthFits,
                isTermsChecked = effect.state
            )
        }

}