package com.roblox.ipo.onboarding.welcome

import android.view.ViewAnimationUtils
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
        onboarding_first_card.post {
            val cx = onboarding_first_card.width / 2
            val cy = onboarding_first_card.height / 2

            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(onboarding_first_card, cx, cy, 0f, finalRadius)
                .setDuration(700)
            // make the view visible and start the animation
            onboarding_first_card.isVisible = true
            anim.start()
        }
    }

    override fun render(viewState: WelcomeViewState) {
    }

}