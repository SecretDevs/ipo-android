package com.roblox.ipo.onboarding.confrimation

import com.roblox.ipo.base.MviAction

sealed class ConfirmationAction : MviAction {

    object LoadPhoneNumberAction : ConfirmationAction()

    data class ValidateCodeAction(
        val code: String
    ) : ConfirmationAction()

    object NavigateBackAction : ConfirmationAction()

    object NavigateToNextAction : ConfirmationAction()

}