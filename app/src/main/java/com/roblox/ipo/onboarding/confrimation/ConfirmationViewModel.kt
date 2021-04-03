package com.roblox.ipo.onboarding.confrimation

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.navigation.Coordinator

class ConfirmationViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator
) : BaseViewModel<ConfirmationViewState, ConfirmationEffect, ConfirmationIntent, ConfirmationAction>() {
    override fun initialState(): ConfirmationViewState = ConfirmationViewState(
        phoneNumber = "",
        isError = false,
        isConfirmed = false
    )

    override fun intentInterpreter(intent: ConfirmationIntent): ConfirmationAction =
        when (intent) {
            ConfirmationIntent.BackArrowClickIntent -> ConfirmationAction.NavigateBackAction
            ConfirmationIntent.ConfirmationNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            ConfirmationIntent.InitialIntent -> ConfirmationAction.LoadPhoneNumberAction
            ConfirmationIntent.NextClickIntent -> ConfirmationAction.NavigateToNextAction
            ConfirmationIntent.ResendCodeIntent -> ConfirmationAction.LoadPhoneNumberAction
            is ConfirmationIntent.ValidateConfirmationCodeIntent -> ConfirmationAction.ValidateCodeAction(
                intent.code
            )
        }

    override suspend fun performAction(action: ConfirmationAction): ConfirmationEffect =
        when (action) {
            ConfirmationAction.LoadPhoneNumberAction -> {

                ConfirmationEffect.PhoneLoadedEffect("pepega")
            }
            ConfirmationAction.NavigateBackAction -> {
                coordinator.pop()
                ConfirmationEffect.NothingEffect
            }
            ConfirmationAction.NavigateToNextAction -> {
                coordinator.navigateToQuizAge()
                ConfirmationEffect.NothingEffect
            }
            is ConfirmationAction.ValidateCodeAction -> {
                when {
                    action.code == "4444" -> ConfirmationEffect.CorrectCodeEffect
                    action.code.length == 4 -> ConfirmationEffect.WrongCodeEffect
                    else -> ConfirmationEffect.NothingEffect
                }
            }
        }

    override fun stateReducer(
        oldState: ConfirmationViewState,
        effect: ConfirmationEffect
    ): ConfirmationViewState =
        when (effect) {
            ConfirmationEffect.CorrectCodeEffect -> ConfirmationViewState(
                phoneNumber = oldState.phoneNumber,
                isError = false,
                isConfirmed = true
            )
            ConfirmationEffect.NothingEffect -> oldState
            is ConfirmationEffect.PhoneLoadedEffect -> ConfirmationViewState(
                phoneNumber = effect.phone,
                isError = false,
                isConfirmed = false
            )
            ConfirmationEffect.WrongCodeEffect -> ConfirmationViewState(
                phoneNumber = oldState.phoneNumber,
                isError = true,
                isConfirmed = false
            )
        }
}