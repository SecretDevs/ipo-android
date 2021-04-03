package com.roblox.ipo.onboarding.login

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class LoginIntent : MviIntent {

    object InitialIntent : LoginIntent(), NothingIntent

    object LoginNothingIntent : LoginIntent(), NothingIntent

    data class LoginClickIntent(
        val phone: String
    ) : LoginIntent()

    object BackArrowClickIntent : LoginIntent()

    data class TermsAcceptStateChangeIntent(
        val isAccepted: Boolean
    ) : LoginIntent()

    data class PhoneLengthChangeIntent(
        val length: Int
    ) : LoginIntent()

}