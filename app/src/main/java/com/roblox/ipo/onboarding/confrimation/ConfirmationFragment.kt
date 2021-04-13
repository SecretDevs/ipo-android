package com.roblox.ipo.onboarding.confrimation

import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login_confirmation.*

@AndroidEntryPoint
class ConfirmationFragment : BaseFragment<ConfirmationViewState, ConfirmationIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_login_confirmation
    override val viewModel: ConfirmationViewModel by viewModels()

    override fun backStackIntent(): ConfirmationIntent =
        ConfirmationIntent.ConfirmationNothingIntent

    override fun initialIntent(): ConfirmationIntent = ConfirmationIntent.InitialIntent

    override fun initViews() {
        btn_arrow_back.setOnClickListener {
            _intentLiveData.value = ConfirmationIntent.BackArrowClickIntent
        }
        login_confirmation_next_btn.setOnClickListener {
            _intentLiveData.value = ConfirmationIntent.NextClickIntent
        }
        login_confirmation_resend_code.setOnClickListener {
            _intentLiveData.value = ConfirmationIntent.ResendCodeIntent
        }

        login_confirmation_card_input1.doOnTextChanged { text, _, _, _ ->
            if (text?.length == 1) {
                jumpToTheFirstEmptyInput()
            }
            if (isCodeCompleted()) {
                _intentLiveData.value = ConfirmationIntent.ValidateConfirmationCodeIntent(getCode())
            }
        }
        login_confirmation_card_input1.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus && login_confirmation_card_input1.text.isNullOrEmpty())
                    jumpToTheFirstEmptyInput()
            }

        login_confirmation_card_input2.doOnTextChanged { text, _, _, _ ->
            if (text?.length == 1) {
                jumpToTheFirstEmptyInput()
            }
            if (isCodeCompleted()) {
                _intentLiveData.value = ConfirmationIntent.ValidateConfirmationCodeIntent(getCode())
            }
        }
        login_confirmation_card_input2.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus && login_confirmation_card_input2.text.isNullOrEmpty())
                    jumpToTheFirstEmptyInput()
            }

        login_confirmation_card_input3.doOnTextChanged { text, _, _, _ ->
            if (text?.length == 1) {
                jumpToTheFirstEmptyInput()
            }
            if (isCodeCompleted()) {
                _intentLiveData.value = ConfirmationIntent.ValidateConfirmationCodeIntent(getCode())
            }
        }
        login_confirmation_card_input3.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus && login_confirmation_card_input3.text.isNullOrEmpty())
                    jumpToTheFirstEmptyInput()
            }

        login_confirmation_card_input4.doOnTextChanged { text, _, _, _ ->
            if (isCodeCompleted()) {
                _intentLiveData.value = ConfirmationIntent.ValidateConfirmationCodeIntent(getCode())
            }
        }
        login_confirmation_card_input4.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus -> if (hasFocus) jumpToTheFirstEmptyInput() }
    }

    override fun render(viewState: ConfirmationViewState) {
        login_confirmation_next_btn.isEnabled = viewState.isConfirmed
        login_confirmation_resend_code.isEnabled = !viewState.isConfirmed
        if (viewState.phoneNumber.isNotEmpty()) {
            login_confirmation_card_phone_number.text = resources.getString(
                R.string.text_login_confirmation_phone_number,
                viewState.phoneNumber
            )
        }
    }

    private fun isCodeCompleted(): Boolean =
        !login_confirmation_card_input1.text.isNullOrEmpty() and
                !login_confirmation_card_input2.text.isNullOrEmpty() and
                !login_confirmation_card_input3.text.isNullOrEmpty() and
                !login_confirmation_card_input4.text.isNullOrEmpty()

    private fun jumpToTheFirstEmptyInput() {
        when {
            login_confirmation_card_input1.text.isNullOrEmpty() -> {
                login_confirmation_card_input1.requestFocus()
            }
            login_confirmation_card_input2.text.isNullOrEmpty() -> {
                login_confirmation_card_input2.requestFocus()
            }
            login_confirmation_card_input3.text.isNullOrEmpty() -> {
                login_confirmation_card_input3.requestFocus()
            }
            login_confirmation_card_input4.text.isNullOrEmpty() -> {
                login_confirmation_card_input4.requestFocus()
            }
        }
    }

    private fun getCode() =
        "${login_confirmation_card_input1.text}${login_confirmation_card_input2.text}${login_confirmation_card_input3.text}${login_confirmation_card_input4.text}"

}