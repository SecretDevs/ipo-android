package com.roblox.ipo.onboarding.confrimation

import com.roblox.ipo.base.MviViewState

data class ConfirmationViewState(
    val phoneNumber: String,
    val isError: Boolean,
    val isConfirmed: Boolean
) : MviViewState {
    override fun log(): String = this.toString()
}