package com.roblox.ipo.data.usecase

import com.roblox.ipo.data.source.DealsDataSource
import com.roblox.ipo.di.RemoteDataSource
import com.roblox.ipo.vo.inapp.Deal
import com.roblox.ipo.vo.inapp.Result
import javax.inject.Inject

interface DealsUseCase {
    suspend fun getFavoriteDeals(lastUpdatedAt: Long?): Result<List<Deal>>
    suspend fun getIpoDeals(lastUpdatedAt: Long?): Result<List<Deal>>
    suspend fun getSpacDeals(lastUpdatedAt: Long?): Result<List<Deal>>
    suspend fun getStocksDeals(lastUpdatedAt: Long?): Result<List<Deal>>
    suspend fun getDealsStates(): Result<List<Boolean>>
    suspend fun addFave(dealId: String): Result<Boolean>
    suspend fun removeFave(dealId: String): Result<Boolean>
}

class DealsUseCaseImpl @Inject constructor(
    @RemoteDataSource private val remoteDealsDataSource: DealsDataSource
) : DealsUseCase {
    override suspend fun getFavoriteDeals(lastUpdatedAt: Long?): Result<List<Deal>> =
        remoteDealsDataSource.getFavoriteDeals(lastUpdatedAt)

    override suspend fun getIpoDeals(lastUpdatedAt: Long?): Result<List<Deal>> =
        remoteDealsDataSource.getIpoDeals(lastUpdatedAt)

    override suspend fun getSpacDeals(lastUpdatedAt: Long?): Result<List<Deal>> =
        remoteDealsDataSource.getSpacDeals(lastUpdatedAt)

    override suspend fun getStocksDeals(lastUpdatedAt: Long?): Result<List<Deal>> =
        remoteDealsDataSource.getStocksDeals(lastUpdatedAt)

    override suspend fun getDealsStates(): Result<List<Boolean>> =
        Result.Success(listOf(true, true, true, false))

    override suspend fun addFave(dealId: String): Result<Boolean> =
        remoteDealsDataSource.addFave(dealId)

    override suspend fun removeFave(dealId: String): Result<Boolean> =
        remoteDealsDataSource.removeFave(dealId)

}