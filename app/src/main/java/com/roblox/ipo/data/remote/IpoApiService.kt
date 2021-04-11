package com.roblox.ipo.data.remote

import com.roblox.ipo.vo.remote.*
import retrofit2.Response
import retrofit2.http.*

interface IpoApiService {
    @FormUrlEncoded
    @POST("login/request-code")
    suspend fun requestSmsCodeToNumber(
        @Field("phone_number") phoneNumber: Long
    ): Response<Status>

    @FormUrlEncoded
    @POST("login/check-code")
    suspend fun verifySmsCodeForNumber(
        @Field("phone_number") phoneNumber: Long,
        @Field("sms_code") code: Int
    ): Response<Token>

    @FormUrlEncoded
    @POST("poll/save")
    suspend fun savePoll(
        @Header("Authorization") token: String,
        @Field("age") age: Int,
        @Field("capital") capital: String,
        @Field("experience") experience: String,
        @Field("goal") goal: String,
        @Field("interest") interest: String,
        @Field("risk") risk: String,
        @Field("tools") vararg tools: String
    ): Response<SavePoll>

    @GET("orders/")
    suspend fun getDeals(
        @Header("Authorization") token: String,
        @Query("type") type: String,
        @Query("last_updated_at") lastUpdatedAt: String? = null
    ): Response<Deals>

    @GET("orders/favourites")
    suspend fun getFavoriteDeals(
        @Header("Authorization") token: String,
        @Query("last_updated_at") lastUpdatedAt: String? = null
    ): Response<Deals>

    @GET("orders/stats")
    suspend fun getStatistic(
        @Header("Authorization") token: String
    ): Response<RemoteStats>

    @FormUrlEncoded
    @POST("orders/add-favourite")
    suspend fun addDealToFavorite(
        @Header("Authorization") token: String,
        @Field("id") dealId: String
    ): Response<Status>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "orders/delete-favourite", hasBody = true)
    suspend fun removeFaveFromDeal(
        @Header("Authorization") token: String,
        @Field("id") dealId: String
    ): Response<Status>

}