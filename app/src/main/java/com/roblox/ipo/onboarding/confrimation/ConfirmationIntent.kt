package com.roblox.ipo.onboarding.confrimation

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class ConfirmationIntent : MviIntent {

    object InitialIntent : ConfirmationIntent()

    object BackArrowClickIntent : ConfirmationIntent()

    object ConfirmationNothingIntent : ConfirmationIntent(), NothingIntent

    data class ValidateConfirmationCodeIntent(
        val code: String
    ) : ConfirmationIntent()

    object ResendCodeIntent : ConfirmationIntent()

    object NextClickIntent : ConfirmationIntent()

}