package com.roblox.ipo.onboarding.login

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewState, LoginIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModels()

    override fun backStackIntent(): LoginIntent =
        LoginIntent.LoginNothingIntent

    override fun initialIntent(): LoginIntent = LoginIntent.InitialIntent

    override fun initViews() {
        btn_arrow_back.setOnClickListener {
            _intentLiveData.value = LoginIntent.BackArrowClickIntent
        }
        login_send_code_btn.setOnClickListener {
            _intentLiveData.value =
                LoginIntent.LoginClickIntent(login_phone_card_input.text.toString())
        }
        login_phone_card_input.doOnTextChanged { text, _, _, _ ->
            _intentLiveData.value =
                LoginIntent.PhoneLengthChangeIntent(text?.length ?: 0)
        }
        login_terms_accept.setOnCheckedChangeListener { _, isChecked ->
            _intentLiveData.value = LoginIntent.TermsAcceptStateChangeIntent(isChecked)
        }
    }

    //TODO: not clickable button style
    override fun render(viewState: LoginViewState) {
        login_terms_accept.isChecked = viewState.isTermsChecked
        login_send_code_btn.isClickable = viewState.isLengthFits && viewState.isTermsChecked
        when (viewState.errorCode) {
        }
    }
}