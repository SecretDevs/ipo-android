package com.roblox.ipo.payment

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class PaymentIntent : MviIntent {

    data class InitialIntent(
        val paymentType: Int,
        val paymentAmount: Int
    ) : PaymentIntent()

    object PayWithCardIntent : PaymentIntent()

    object PayWithGooglePayIntent : PaymentIntent()

    object BackArrowClickIntent : PaymentIntent()

    object PaymentNothingIntent : PaymentIntent(), NothingIntent

    object ShowFormIntent : PaymentIntent()

    data class CardNumberChangeIntent(
        val cardNumberLength: Int
    ) : PaymentIntent()

    data class CvvLengthChangeIntent(
        val cvvLength: Int
    ) : PaymentIntent()

    data class DateLengthChangeIntent(
        val isEmpty: Boolean
    ) : PaymentIntent()

    data class HolderLengthChangeIntent(
        val isEmpty: Boolean
    ) : PaymentIntent()

}