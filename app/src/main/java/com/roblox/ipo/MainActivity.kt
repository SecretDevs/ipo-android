package com.roblox.ipo

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.roblox.ipo.navigation.Coordinator
import com.roblox.ipo.onboarding.confrimation.ConfirmationFragment
import com.roblox.ipo.onboarding.login.LoginFragment
import com.roblox.ipo.onboarding.quiz.*
import com.roblox.ipo.onboarding.quiz.risk.QuizRiskFragment
import com.roblox.ipo.onboarding.welcome.WelcomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var coordinator: Coordinator

    private val bottomNavigationViewVisibilityCallback =
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                bottom_navigation.isVisible = !bottomNavigationlessViewFragments.contains(f::class)

                if (Build.VERSION.SDK_INT in Build.VERSION_CODES.M..Build.VERSION_CODES.R) {
                    if (!darkStatusBarFragments.contains(f::class)) {
                        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            coordinator.start()
            bottom_navigation.selectedItemId = LAST_ITEM
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(
            bottomNavigationViewVisibilityCallback,
            false
        )
        makeStatusBarTransparent()

        bottom_navigation.setOnNavigationItemSelectedListener {
            bottom_navigation.isVisible = true
            when (it.itemId) {
                R.id.page_deals -> {
                    if (LAST_ITEM != R.id.page_deals) {
                        coordinator.navigateToDeals()
                    }
                }
                R.id.page_courses -> {
                    if (LAST_ITEM != R.id.page_courses) {

                    }
                }
                R.id.page_ipo -> {
                    if (LAST_ITEM != R.id.page_ipo) {

                    }
                }
                R.id.page_news -> {
                    if (LAST_ITEM != R.id.page_news) {

                    }
                }
                R.id.page_settings -> {
                    if (LAST_ITEM != R.id.page_settings) {

                    }
                }
                else -> {
                    Timber.e("Unknown item id ${it.itemId}")
                    return@setOnNavigationItemSelectedListener false
                }
            }
            if (LAST_ITEM == it.itemId) {
                false
            } else {
                LAST_ITEM = it.itemId
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(
            bottomNavigationViewVisibilityCallback
        )
    }

    companion object {
        private var LAST_ITEM: Int = R.id.page_deals
        private val bottomNavigationlessViewFragments = setOf(
            WelcomeFragment::class,
            LoginFragment::class,
            ConfirmationFragment::class,
            QuizAgeFragment::class,
            QuizExperienceFragment::class,
            QuizFundFragment::class,
            QuizProfitabilityFragment::class,
            QuizRiskFragment::class,
            QuizTargetFragment::class,
            QuizToolsFragment::class
        )
        private val darkStatusBarFragments = setOf(
            WelcomeFragment::class
        )
    }
}

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}