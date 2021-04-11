package com.roblox.ipo.payment

import com.roblox.ipo.base.MviAction

sealed class PaymentAction : MviAction {

    data class LoadIntentDataAction(
        val paymentType: Int,
        val paymentAmount: Int
    ) : PaymentAction()

    object PaymentWithCardAction : PaymentAction()

    object PaymentWithGooglePayAction : PaymentAction()

    object NavigateBackAction : PaymentAction()

    data class CardNumberChangeAction(
        val cardNumberLength: Int
    ) : PaymentAction()

    data class CvvLengthChangeAction(
        val cvvLength: Int
    ) : PaymentAction()

    data class DateLengthChangeAction(
        val isEmpty: Boolean
    ) : PaymentAction()

    data class HolderLengthChangeAction(
        val isEmpty: Boolean
    ) : PaymentAction()

    object ShowFormAction : PaymentAction()

}