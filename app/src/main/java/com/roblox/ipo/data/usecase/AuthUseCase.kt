package com.roblox.ipo.data.usecase

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface AuthUseCase {
    fun saveUserPhoneNumber(phoneNumber: String)
    fun getUserPhoneNumber(): String
}

class AuthUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context
) : AuthUseCase {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun saveUserPhoneNumber(phoneNumber: String) {
        pref.edit {
            putString(PHONE_FIELD, phoneNumber)
        }
    }

    override fun getUserPhoneNumber(): String = pref.getString(PHONE_FIELD, "") ?: ""

    companion object {
        private const val PREF_NAME = "AUTH_PREF"
        private const val PHONE_FIELD = "AUTH_PHONE"
    }

}