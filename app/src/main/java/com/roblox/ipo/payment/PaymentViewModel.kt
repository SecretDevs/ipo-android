package com.roblox.ipo.payment

import androidx.hilt.lifecycle.ViewModelInject
import com.roblox.ipo.base.BaseViewModel
import com.roblox.ipo.navigation.Coordinator

class PaymentViewModel @ViewModelInject constructor(
    private val coordinator: Coordinator
) : BaseViewModel<PaymentViewState, PaymentEffect, PaymentIntent, PaymentAction>() {
    override fun initialState(): PaymentViewState = PaymentViewState(
        paymentAmount = 0,
        paymentType = 0,
        isCardNumberValid = false,
        isCvvLengthValid = false,
        isDateEntered = false,
        isHolderNameEntered = false,
        isPaymentWithGooglePay = false,
        isPaymentProceeding = false,
        isPayed = null,
        paymentProceedingError = null
    )

    override fun intentInterpreter(intent: PaymentIntent): PaymentAction =
        when (intent) {
            PaymentIntent.BackArrowClickIntent -> PaymentAction.NavigateBackAction
            is PaymentIntent.CardNumberChangeIntent -> PaymentAction.CardNumberChangeAction(intent.cardNumberLength)
            is PaymentIntent.CvvLengthChangeIntent -> PaymentAction.CvvLengthChangeAction(intent.cvvLength)
            is PaymentIntent.DateLengthChangeIntent -> PaymentAction.DateLengthChangeAction(intent.isEmpty)
            is PaymentIntent.HolderLengthChangeIntent -> PaymentAction.HolderLengthChangeAction(
                intent.isEmpty
            )
            is PaymentIntent.InitialIntent -> PaymentAction.LoadIntentDataAction(
                intent.paymentType,
                intent.paymentAmount
            )
            PaymentIntent.PayWithCardIntent -> PaymentAction.PaymentWithCardAction
            PaymentIntent.PayWithGooglePayIntent -> PaymentAction.PaymentWithGooglePayAction
            PaymentIntent.PaymentNothingIntent -> throw IllegalArgumentException("Nothing intent interpreting")
            PaymentIntent.ShowFormIntent -> PaymentAction.PaymentWithCardAction
        }

    override suspend fun performAction(action: PaymentAction): PaymentEffect =
        when (action) {
            is PaymentAction.CardNumberChangeAction -> PaymentEffect.CardNumberLengthIsEnoughEffect(
                action.cardNumberLength == 19
            )
            is PaymentAction.CvvLengthChangeAction -> PaymentEffect.CvvLengthIsEnoughEffect(
                action.cvvLength == 3
            )
            is PaymentAction.DateLengthChangeAction -> PaymentEffect.DateLengthIsEnoughEffect(
                !action.isEmpty
            )
            is PaymentAction.HolderLengthChangeAction -> PaymentEffect.HolderLengthIsEnoughEffect(
                !action.isEmpty
            )
            is PaymentAction.LoadIntentDataAction -> PaymentEffect.IntentDataLoadedEffect(
                action.paymentType,
                action.paymentAmount
            )
            PaymentAction.NavigateBackAction -> {
                coordinator.pop()
                PaymentEffect.NothingEffect
            }
            PaymentAction.PaymentWithCardAction -> PaymentEffect.PaymentPassedEffect
            PaymentAction.PaymentWithGooglePayAction -> PaymentEffect.PaymentErrorEffect(Throwable(""))
            PaymentAction.ShowFormAction -> TODO()
        }

    override fun stateReducer(oldState: PaymentViewState, effect: PaymentEffect): PaymentViewState =
        when (effect) {
            is PaymentEffect.CvvLengthIsEnoughEffect -> PaymentViewState(
                paymentAmount = oldState.paymentAmount,
                paymentType = oldState.paymentType,
                isCardNumberValid = oldState.isCardNumberValid,
                isCvvLengthValid = effect.isEnough,
                isDateEntered = oldState.isDateEntered,
                isHolderNameEntered = oldState.isHolderNameEntered,
                isPaymentWithGooglePay = false,
                isPaymentProceeding = false,
                isPayed = null,
                paymentProceedingError = null
            )
            is PaymentEffect.DateLengthIsEnoughEffect -> PaymentViewState(
                paymentAmount = oldState.paymentAmount,
                paymentType = oldState.paymentType,
                isCardNumberValid = oldState.isCardNumberValid,
                isCvvLengthValid = oldState.isCvvLengthValid,
                isDateEntered = effect.isEnough,
                isHolderNameEntered = oldState.isHolderNameEntered,
                isPaymentWithGooglePay = false,
                isPaymentProceeding = false,
                isPayed = null,
                paymentProceedingError = null
            )
            is PaymentEffect.HolderLengthIsEnoughEffect -> PaymentViewState(
                paymentAmount = oldState.paymentAmount,
                paymentType = oldState.paymentType,
                isCardNumberValid = oldState.isCardNumberValid,
                isCvvLengthValid = oldState.isCvvLengthValid,
                isDateEntered = oldState.isDateEntered,
                isHolderNameEntered = effect.isEnough,
                isPaymentWithGooglePay = false,
                isPaymentProceeding = false,
                isPayed = null,
                paymentProceedingError = null
            )
            is PaymentEffect.IntentDataLoadedEffect -> PaymentViewState(
                paymentAmount = effect.paymentAmount,
                paymentType = effect.paymentType,
                isCardNumberValid = false,
                isCvvLengthValid = false,
                isDateEntered = false,
                isHolderNameEntered = false,
                isPaymentWithGooglePay = false,
                isPaymentProceeding = false,
                isPayed = null,
                paymentProceedingError = null
            )
            is PaymentEffect.PaymentErrorEffect -> PaymentViewState(
                paymentAmount = oldState.paymentAmount,
                paymentType = oldState.paymentType,
                isCardNumberValid = oldState.isCardNumberValid,
                isCvvLengthValid = oldState.isCvvLengthValid,
                isDateEntered = oldState.isDateEntered,
                isHolderNameEntered = oldState.isHolderNameEntered,
                isPaymentWithGooglePay = oldState.isPaymentWithGooglePay,
                isPaymentProceeding = false,
                isPayed = false,
                paymentProceedingError = effect.throwable
            )
            PaymentEffect.PaymentPassedEffect -> PaymentViewState(
                paymentAmount = oldState.paymentAmount,
                paymentType = oldState.paymentType,
                isCardNumberValid = oldState.isCardNumberValid,
                isCvvLengthValid = oldState.isCvvLengthValid,
                isDateEntered = oldState.isDateEntered,
                isHolderNameEntered = oldState.isHolderNameEntered,
                isPaymentWithGooglePay = oldState.isPaymentWithGooglePay,
                isPaymentProceeding = false,
                isPayed = true,
                paymentProceedingError = null
            )
            PaymentEffect.CardPaymentProceedingEffect -> PaymentViewState(
                paymentAmount = oldState.paymentAmount,
                paymentType = oldState.paymentType,
                isCardNumberValid = oldState.isCardNumberValid,
                isCvvLengthValid = oldState.isCvvLengthValid,
                isDateEntered = oldState.isDateEntered,
                isHolderNameEntered = oldState.isHolderNameEntered,
                isPaymentWithGooglePay = false,
                isPaymentProceeding = true,
                isPayed = null,
                paymentProceedingError = null
            )
            is PaymentEffect.CardNumberLengthIsEnoughEffect -> PaymentViewState(
                paymentAmount = oldState.paymentAmount,
                paymentType = oldState.paymentType,
                isCardNumberValid = effect.isEnough,
                isCvvLengthValid = oldState.isCvvLengthValid,
                isDateEntered = oldState.isDateEntered,
                isHolderNameEntered = oldState.isHolderNameEntered,
                isPaymentWithGooglePay = false,
                isPaymentProceeding = false,
                isPayed = null,
                paymentProceedingError = null
            )
            PaymentEffect.NothingEffect -> oldState
            PaymentEffect.GooglePayPaymentProceedingEffect -> PaymentViewState(
                paymentAmount = oldState.paymentAmount,
                paymentType = oldState.paymentType,
                isCardNumberValid = oldState.isCardNumberValid,
                isCvvLengthValid = oldState.isCvvLengthValid,
                isDateEntered = oldState.isDateEntered,
                isHolderNameEntered = oldState.isHolderNameEntered,
                isPaymentWithGooglePay = true,
                isPaymentProceeding = true,
                isPayed = null,
                paymentProceedingError = null
            )
            PaymentEffect.ShowFormEffect -> TODO()
        }
}