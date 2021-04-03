package com.roblox.ipo.onboarding.confrimation

import com.roblox.ipo.base.MviEffect

sealed class ConfirmationEffect : MviEffect {

    object CorrectCodeEffect : ConfirmationEffect()

    object WrongCodeEffect : ConfirmationEffect()

    object NothingEffect : ConfirmationEffect()

    data class PhoneLoadedEffect(
        val phone: String
    ) : ConfirmationEffect()

}