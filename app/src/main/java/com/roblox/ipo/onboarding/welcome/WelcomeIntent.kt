package com.roblox.ipo.onboarding.welcome

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class WelcomeIntent : MviIntent {

    object InitialIntent : WelcomeIntent()

    object WelcomeNothingIntent : WelcomeIntent(), NothingIntent

    object LoginClickIntent : WelcomeIntent()

}