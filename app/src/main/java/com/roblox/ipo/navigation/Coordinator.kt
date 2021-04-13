package com.roblox.ipo.navigation

import androidx.fragment.app.FragmentActivity
import com.roblox.ipo.R
import com.roblox.ipo.data.usecase.AuthUseCase
import com.roblox.ipo.deals.DealsFragment
import com.roblox.ipo.deals.statistic.StatisticFragment
import com.roblox.ipo.onboarding.confrimation.ConfirmationFragment
import com.roblox.ipo.onboarding.login.LoginFragment
import com.roblox.ipo.onboarding.quiz.*
import com.roblox.ipo.onboarding.quiz.risk.QuizRiskFragment
import com.roblox.ipo.onboarding.welcome.WelcomeFragment
import com.roblox.ipo.payment.PaymentFragment
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

interface Coordinator {
    fun navigateToOnboarding()
    fun navigateToLogin()
    fun navigateToConfirmation()
    fun navigateToQuizAge()
    fun navigateToQuizFund()
    fun navigateToQuizExperience()
    fun navigateToQuizTools()
    fun navigateToQuizTarget()
    fun navigateToQuizProfitability()
    fun navigateToQuizRisk()
    fun navigateToDeals()
    fun navigateToStatistic()
    fun navigateToPayment(paymentType: Int, paymentAmount: Int)
    fun start()
    fun pop()
}

@ActivityScoped
class CoordinatorImpl @Inject constructor(
    @ActivityScoped activity: FragmentActivity,
    private val authUseCase: AuthUseCase
) : Coordinator {
    private val fragmentManager = activity.supportFragmentManager

    override fun navigateToOnboarding() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, WelcomeFragment())
            .commitAllowingStateLoss()
    }

    override fun navigateToLogin() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, LoginFragment())
            .addToBackStack("ONBOARDING")
            .commitAllowingStateLoss()
    }

    override fun navigateToConfirmation() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, ConfirmationFragment())
            .addToBackStack("LOGIN")
            .commitAllowingStateLoss()
    }

    override fun navigateToQuizAge() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, QuizAgeFragment())
            .commitAllowingStateLoss()
    }

    override fun navigateToQuizFund() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, QuizFundFragment())
            .addToBackStack("AGE")
            .commitAllowingStateLoss()
    }

    override fun navigateToQuizExperience() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, QuizExperienceFragment())
            .addToBackStack("FUND")
            .commitAllowingStateLoss()
    }

    override fun navigateToQuizTools() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, QuizToolsFragment())
            .addToBackStack("EXPERIENCE")
            .commitAllowingStateLoss()
    }

    override fun navigateToQuizTarget() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, QuizTargetFragment())
            .addToBackStack("TOOLS")
            .commitAllowingStateLoss()
    }

    override fun navigateToQuizProfitability() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, QuizProfitabilityFragment())
            .addToBackStack("TARGET")
            .commitAllowingStateLoss()
    }

    override fun navigateToQuizRisk() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, QuizRiskFragment())
            .addToBackStack("PROFITABILITY")
            .commitAllowingStateLoss()
    }

    override fun navigateToDeals() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, DealsFragment.newInstance())
            .commitAllowingStateLoss()
    }

    override fun navigateToStatistic() {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment_container, StatisticFragment())
            .addToBackStack("DEALS")
            .commitAllowingStateLoss()
    }

    override fun navigateToPayment(paymentType: Int, paymentAmount: Int) {
        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(
                R.id.fragment_container,
                PaymentFragment.newInstance(paymentType, paymentAmount)
            )
            .addToBackStack("DEALS")
            .commitAllowingStateLoss()
    }

    override fun start() {
        if (authUseCase.isTokenExists())
            navigateToDeals()
        else
            navigateToOnboarding()
    }

    override fun pop() {
        fragmentManager.popBackStack()
    }

    private fun clearBackStack() {
        repeat(fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

}