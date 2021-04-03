package com.roblox.ipo.onboarding.welcome

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_onboarding.*

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<WelcomeViewState, WelcomeIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_onboarding
    override val viewModel: WelcomeViewModel by viewModels()

    override fun backStackIntent(): WelcomeIntent = WelcomeIntent.WelcomeNothingIntent

    override fun initialIntent(): WelcomeIntent = WelcomeIntent.InitialIntent

    override fun initViews() {
        onboarding_sign_in.setOnClickListener {
            _intentLiveData.value = WelcomeIntent.LoginClickIntent
        }
    }

    override fun render(viewState: WelcomeViewState) {
        if (viewState.isLogoShown) {
            onboarding_first_card.isVisible = true //TODO: remove with animation
        } else {
            onboarding_first_card.isVisible = false
        }
    }
    
}