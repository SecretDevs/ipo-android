package com.roblox.ipo.data.usecase

import android.content.Context
import androidx.core.content.edit
import com.roblox.ipo.data.remote.IpoApiService
import com.roblox.ipo.vo.inapp.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

interface AuthUseCase {
    fun saveUserPhoneNumber(phoneNumber: String)
    fun getUserPhoneNumber(): String
    fun getUserToken(): String
    suspend fun requestCodeForPhoneNumber(phoneNumber: String): Result<Boolean>
    suspend fun checkRequestedCode(code: String): Result<Boolean>
}

class AuthUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val apiService: IpoApiService
) : AuthUseCase {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun saveUserPhoneNumber(phoneNumber: String) {
        pref.edit {
            putString(PHONE_FIELD, phoneNumber)
        }
    }

    override fun getUserPhoneNumber(): String = pref.getString(PHONE_FIELD, "") ?: ""

    private fun saveUserToken(token: String?) {
        pref.edit {
            putString(TOKEN_FIELD, token)
        }
    }

    override fun getUserToken(): String = "Bearer ${pref.getString(TOKEN_FIELD, "")}" ?: ""

    override suspend fun requestCodeForPhoneNumber(phoneNumber: String): Result<Boolean> {
        val numberAsLong = phoneNumberFromStringToLong(phoneNumber)
            ?: return Result.Error(Throwable("wrong phone number"))
        val response = apiService.requestSmsCodeToNumber(
            phoneNumber = numberAsLong
        )
        if (response.isSuccessful && response.body() != null && response.body()?.status == "OK") {
            return Result.Success(true)
        } else if (response.isSuccessful) (
                return Result.Success(false)
                ) else {
            return Result.Error(Throwable(response.errorBody()?.string()))
        }
    }

    override suspend fun checkRequestedCode(code: String): Result<Boolean> {
        val numberAsLong = phoneNumberFromStringToLong(getUserPhoneNumber())
            ?: return Result.Error(Throwable("wrong phone number"))
        val response = apiService.verifySmsCodeForNumber(
            phoneNumber = numberAsLong,
            code = code.toInt()
        )
        if (response.isSuccessful && response.body() != null) {
            saveUserToken(response.body()?.token)
            return Result.Success(true)
        } else if (response.isSuccessful) {
            return Result.Success(false)
        } else {
            return Result.Error(Throwable(response.errorBody()?.string()))
        }
    }

    private fun phoneNumberFromStringToLong(phoneNumber: String): Long? =
        phoneNumber.filter { !phoneNumberFilter.contains(it) }.toLongOrNull()

    companion object {
        private const val PREF_NAME = "AUTH_PREF"
        private const val PHONE_FIELD = "AUTH_PHONE"
        private const val TOKEN_FIELD = "AUTH_TOKEN"

        private val phoneNumberFilter = setOf('(', ')', '+', ' ', '-')
    }

}