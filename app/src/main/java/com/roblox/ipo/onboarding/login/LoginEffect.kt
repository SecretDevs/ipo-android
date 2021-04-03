package com.roblox.ipo.onboarding.login

import com.roblox.ipo.base.MviEffect

sealed class LoginEffect : MviEffect {

    data class WrongPhoneEffect(
        val errorCode: Int
    ) : LoginEffect()

    data class TermsChangeEffect(
        val state: Boolean
    ) : LoginEffect()

    data class PhoneLengthChangeEffect(
        val isLengthFits: Boolean
    ) : LoginEffect()

    object NothingEffect : LoginEffect()

}