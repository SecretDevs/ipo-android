package com.roblox.ipo.payment

import com.roblox.ipo.base.MviViewState

data class PaymentViewState(
    val paymentType: Int,
    val paymentAmount: Int,
    val isCardNumberValid: Boolean,
    val isCvvLengthValid: Boolean,
    val isDateEntered: Boolean,
    val isHolderNameEntered: Boolean,
    val isPaymentWithGooglePay: Boolean,
    val isPaymentProceeding: Boolean,
    val paymentProceedingError: Throwable?,
    val isPayed: Boolean?
) : MviViewState {
    override fun log(): String = this.toString()
}