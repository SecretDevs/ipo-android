package com.roblox.ipo.payment

import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import com.roblox.ipo.utils.CardNumberTextWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment_error.*
import kotlinx.android.synthetic.main.fragment_payment_ok.*

@AndroidEntryPoint
class PaymentFragment : BaseFragment<PaymentViewState, PaymentIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_payment
    override val viewModel: PaymentViewModel by viewModels()

    override fun backStackIntent(): PaymentIntent = PaymentIntent.PaymentNothingIntent

    override fun initialIntent(): PaymentIntent = PaymentIntent.InitialIntent(
        paymentType = arguments?.getInt(PAYMENT_TYPE) ?: 0,
        paymentAmount = arguments?.getInt(PAYMENT_PRICE) ?: 0
    )

    override fun initViews() {
        btn_arrow_back.setOnClickListener {
            _intentLiveData.value = PaymentIntent.BackArrowClickIntent
        }
        payment_with_card_btn.setOnClickListener {
            _intentLiveData.value = PaymentIntent.PayWithCardIntent
        }
        payment_with_google_pay_btn.setOnClickListener {
            _intentLiveData.value = PaymentIntent.PayWithGooglePayIntent
        }

        payment_card_layout_card_cvv.doOnTextChanged { text, _, _, _ ->
            _intentLiveData.value = PaymentIntent.CvvLengthChangeIntent(text?.length ?: 0)
        }
        payment_card_layout_card_expire.doOnTextChanged { text, _, _, _ ->
            _intentLiveData.value = PaymentIntent.DateLengthChangeIntent(text.isNullOrEmpty())
        }
        payment_card_layout_card_holder.doOnTextChanged { text, _, _, _ ->
            _intentLiveData.value = PaymentIntent.HolderLengthChangeIntent(text.isNullOrEmpty())
        }
        payment_card_layout_card_number.doOnTextChanged { text, _, _, _ ->
            _intentLiveData.value = PaymentIntent.CardNumberChangeIntent(text?.length ?: 0)

            var matched = false
            if (text?.length ?: 0 >= 2) {
                patterns.forEach {
                    if (text?.substring(0, 2)?.matches(it.first) == true) {
                        matched = true
                        payment_card_layout_card_number.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            it.second,
                            0
                        )
                    }
                }
            }
            if (!matched) {
                payment_card_layout_card_number.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0
                )
            }
        }

        payment_card_layout_card_number.addTextChangedListener(CardNumberTextWatcher())

        payment_btn_status_ok.setOnClickListener {
            _intentLiveData.value = PaymentIntent.BackArrowClickIntent
        }
        payment_btn_status_error.setOnClickListener {
            _intentLiveData.value = PaymentIntent.ShowFormIntent
        }
        //TODO: autofill workaround
    }

    override fun render(viewState: PaymentViewState) {
        payment_with_card_btn.isEnabled =
            viewState.isHolderNameEntered && viewState.isDateEntered &&
                    viewState.isCvvLengthValid && viewState.isCardNumberValid
        appbar_title.text = resources.getString(
            R.string.title_payment,
            when (viewState.paymentType) {
                1 -> resources.getString(R.string.title_payment_section)
                2 -> resources.getString(R.string.title_payment_course)
                else -> ""
            }
        )
        payment_amount_value.text = resources.getString(
            R.string.text_locked_deal_buy_price,
            viewState.paymentAmount
        )
        val isFormOpened = viewState.isPayed != true && viewState.paymentProceedingError == null

        status_ok.isVisible = viewState.isPayed == true
        status_error.isVisible = viewState.paymentProceedingError != null
        payment_card.isVisible = isFormOpened
        payment_amount_value.isVisible = isFormOpened
        payment_amount_text.isVisible = isFormOpened
        payment_with_card_btn.isVisible = isFormOpened
        payment_with_google_pay_btn.isVisible = isFormOpened
    }

    companion object {
        fun newInstance(paymentType: Int, paymentAmount: Int) =
            PaymentFragment().also {
                it.arguments = bundleOf(
                    PAYMENT_TYPE to paymentType,
                    PAYMENT_PRICE to paymentAmount
                )
            }

        private const val PAYMENT_TYPE = "PAYMENT_TYPE"
        private const val PAYMENT_PRICE = "PAYMENT_PRICE"

        private val patterns = listOf(
            Regex("^4[0-9]$") to R.drawable.ic_visa_logo,
            Regex("^5[1-5]$") to R.drawable.ic_mc_symbol
        )
    }

}