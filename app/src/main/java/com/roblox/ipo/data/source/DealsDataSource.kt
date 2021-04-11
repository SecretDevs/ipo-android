package com.roblox.ipo.data.source

import com.roblox.ipo.data.remote.IpoApiService
import com.roblox.ipo.data.usecase.AuthUseCase
import com.roblox.ipo.vo.inapp.Deal
import com.roblox.ipo.vo.inapp.Result
import com.roblox.ipo.vo.mapper.DealMapper
import timber.log.Timber
import javax.inject.Inject

interface DealsDataSource {
    suspend fun getFavoriteDeals(lastUpdatedAt: Long?): Result<List<Deal>>
    suspend fun getIpoDeals(lastUpdatedAt: Long?): Result<List<Deal>>
    suspend fun getSpacDeals(lastUpdatedAt: Long?): Result<List<Deal>>
    suspend fun getStocksDeals(lastUpdatedAt: Long?): Result<List<Deal>>
    suspend fun getDealsStates(): Result<List<Boolean>>
    suspend fun addFave(dealId: String): Result<Boolean>
    suspend fun removeFave(dealId: String): Result<Boolean>
}

class RemoteDealsDataSourceImpl @Inject constructor(
    private val apiService: IpoApiService,
    private val mapper: DealMapper,
    private val authUseCase: AuthUseCase
) : DealsDataSource {

    override suspend fun getFavoriteDeals(lastUpdatedAt: Long?): Result<List<Deal>> {
        val response = apiService.getFavoriteDeals(
            token = authUseCase.getUserToken(),
            lastUpdatedAt = null
        )
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.orders.map(mapper::fromRemoteToInapp))
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

    override suspend fun getIpoDeals(lastUpdatedAt: Long?): Result<List<Deal>> {
        val response = apiService.getDeals(
            token = authUseCase.getUserToken(),
            type = "IPO",
            lastUpdatedAt = ""
        )
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.orders.map(mapper::fromRemoteToInapp))
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

    override suspend fun getSpacDeals(lastUpdatedAt: Long?): Result<List<Deal>> {
        val response = apiService.getDeals(
            token = authUseCase.getUserToken(),
            type = "SPAC",
            lastUpdatedAt = ""
        )
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.orders.map(mapper::fromRemoteToInapp))
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

    override suspend fun getStocksDeals(lastUpdatedAt: Long?): Result<List<Deal>> {
        val response = apiService.getDeals(
            token = authUseCase.getUserToken(),
            type = "STOCK",
            lastUpdatedAt = ""
        )
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.orders.map(mapper::fromRemoteToInapp))
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

    override suspend fun getDealsStates(): Result<List<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun addFave(dealId: String): Result<Boolean> {
        val response = apiService.addDealToFavorite(
            token = authUseCase.getUserToken(),
            dealId = dealId
        )
        Timber.d("${response.code()}")
        Timber.d(response.message())
        return if (response.isSuccessful && response.body() != null && response.body()?.status == "OK") {
            Result.Success(true)
        } else if (response.isSuccessful) {
            Result.Success(false)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

    override suspend fun removeFave(dealId: String): Result<Boolean> {
        val response = apiService.removeFaveFromDeal(
            token = authUseCase.getUserToken(),
            dealId = dealId
        )
        Timber.d("${response.code()}")
        Timber.d(response.message())
        return if (response.isSuccessful && response.body() != null && response.body()?.status == "OK") {
            Result.Success(true)
        } else if (response.isSuccessful) {
            Result.Success(false)
        } else {
            Result.Error(Throwable(response.message()))
        }
    }

}