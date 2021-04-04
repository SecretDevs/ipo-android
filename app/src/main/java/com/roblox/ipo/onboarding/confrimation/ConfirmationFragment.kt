package com.roblox.ipo.onboarding.confrimation

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
            _intentLiveData.value = ConfirmationIntent.ValidateConfirmationCodeIntent("4444")
        }

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

}