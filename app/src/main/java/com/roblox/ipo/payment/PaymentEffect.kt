package com.roblox.ipo.payment

import com.roblox.ipo.base.MviEffect

sealed class PaymentEffect : MviEffect {

    data class IntentDataLoadedEffect(
        val paymentType: Int,
        val paymentAmount: Int
    ) : PaymentEffect()

    data class CardNumberLengthIsEnoughEffect(
        val isEnough: Boolean
    ) : PaymentEffect()

    data class CvvLengthIsEnoughEffect(
        val isEnough: Boolean
    ) : PaymentEffect()

    data class HolderLengthIsEnoughEffect(
        val isEnough: Boolean
    ) : PaymentEffect()

    data class DateLengthIsEnoughEffect(
        val isEnough: Boolean
    ) : PaymentEffect()

    object CardPaymentProceedingEffect : PaymentEffect()

    object GooglePayPaymentProceedingEffect : PaymentEffect()

    object PaymentPassedEffect : PaymentEffect()

    data class PaymentErrorEffect(
        val throwable: Throwable
    ) : PaymentEffect()

    object NothingEffect : PaymentEffect()

    object ShowFormEffect : PaymentEffect()

}