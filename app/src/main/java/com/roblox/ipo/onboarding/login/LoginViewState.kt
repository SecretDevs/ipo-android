package com.roblox.ipo.onboarding.login

import com.roblox.ipo.base.MviViewState

data class LoginViewState(
    val errorCode: Int,
    val isLengthFits: Boolean,
    val isTermsChecked: Boolean
) : MviViewState {
    override fun log(): String = this.toString()
}