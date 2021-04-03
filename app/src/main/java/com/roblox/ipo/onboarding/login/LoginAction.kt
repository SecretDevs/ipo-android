package com.roblox.ipo.onboarding.login

import com.roblox.ipo.base.MviAction

sealed class LoginAction : MviAction {

    data class NavigateToConfirmationAction(
        val phone: String
    ) : LoginAction()

    object NavigateBackAction : LoginAction()

    data class TermsAcceptStateChangeAction(
        val isAccepted: Boolean
    ) : LoginAction()

    data class PhoneLengthChangeAction(
        val length: Int
    ) : LoginAction()

}