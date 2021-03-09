package com.roblox.ipo.deals

import com.roblox.ipo.base.MviEffect

sealed class DealsEffect : MviEffect {

    object InitialLoadingEffect : DealsEffect()

    data class InitialLoadingErrorEffect(
        val throwable: Throwable?
    ) : DealsEffect()

    data class DealsStatesLoadedEffect(
        val states: List<Boolean>
    ) : DealsEffect()

    object NothingEffect : DealsEffect()

}