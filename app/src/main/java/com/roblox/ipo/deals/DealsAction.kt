package com.roblox.ipo.deals

import com.roblox.ipo.base.MviAction

sealed class DealsAction : MviAction {

    object LoadDealsOpenStatesAction : DealsAction()

    object NavigateToDealsStatisticAction : DealsAction()

}