package com.roblox.ipo.data.usecase

import com.roblox.ipo.data.remote.IpoApiService
import com.roblox.ipo.vo.inapp.Result
import com.roblox.ipo.vo.inapp.Stats
import com.roblox.ipo.vo.mapper.StatsMapper
import javax.inject.Inject

interface StatisticUseCase {
    suspend fun getPortfolio(): Result<Stats>
}

class StatisticUseCaseImpl @Inject constructor(
    private val apiService: IpoApiService,
    private val authUseCase: AuthUseCase,
    private val statsMapper: StatsMapper
) : StatisticUseCase {
    override suspend fun getPortfolio(): Result<Stats> {
        val response = apiService.getStatistic(authUseCase.getUserToken())
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(statsMapper.fromRemoteToInapp(response.body()!!))
        } else {
            Result.Error(Throwable(response.message()))
        }
    }
}