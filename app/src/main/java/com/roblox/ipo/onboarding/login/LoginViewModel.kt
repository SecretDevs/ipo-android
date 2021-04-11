package com.roblox.ipo.onboarding.login

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.data.usecase.AuthUseCase
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.vo.inapp.Result

class LoginViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator,
    private val authUseCase: AuthUseCase
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
                authUseCase.saveUserPhoneNumber(action.phone)
                when (val result = authUseCase.requestCodeForPhoneNumber(action.phone)) {
                    is Result.Error -> LoginEffect.WrongPhoneEffect(1)
                    is Result.Success ->
                        if (result.data) {
                            coordinator.navigateToConfirmation()
                            LoginEffect.NothingEffect
                        } else {
                            LoginEffect.WrongPhoneEffect(1)
                        }
                }
            }
            is LoginAction.PhoneLengthChangeAction -> {
                val isProbablyRussianNumber = action.length == WELL_FORMATTED_RUSSIAN_NUMBER_LENGTH
                LoginEffect.PhoneLengthChangeEffect(isProbablyRussianNumber)
            }
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

    companion object {
        private const val WELL_FORMATTED_RUSSIAN_NUMBER_LENGTH = 16
    }

}