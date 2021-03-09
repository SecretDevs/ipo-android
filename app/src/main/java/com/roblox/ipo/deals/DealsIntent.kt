package com.roblox.ipo.deals

import com.roblox.ipo.base.MviIntent
import com.roblox.ipo.base.NothingIntent

sealed class DealsIntent : MviIntent {

    object LoadDealsOpenStatesIntent : DealsIntent()

    object ReloadDealsOpenStatesIntent : DealsIntent()

    object DealsNothingIntent : DealsIntent(), NothingIntent

    object OpenDealsStatisticIntent : DealsIntent()

}