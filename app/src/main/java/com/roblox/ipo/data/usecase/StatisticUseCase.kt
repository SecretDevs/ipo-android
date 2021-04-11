package com.roblox.ipo.data.usecase

import com.roblox.ipo.data.remote.IpoApiService
import com.roblox.ipo.vo.inapp.Statistic
import com.roblox.ipo.vo.inapp.Stats
import com.roblox.ipo.vo.inapp.Result
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

class FakeStatisticUseCase @Inject constructor(

) : StatisticUseCase {

    override suspend fun getPortfolio(): Result<Stats> = Result.Success(
        Stats(
            profitPercent = 74,
            totalProfit = 178841,
            data = listOf(
                0,
                25000,
                10000,
                60000,
                61000,
                100000,
                40000,
                50000,
                30000,
                35000,
                40000,
                65000,
                55000,
                80000,
                40000,
                70000,
                75000,
                178841
            ),
            portfolioItems = listOf(
                Statistic(
                    date = 1614577752,
                    name = "Levi's",
                    profitPercent = 26,
                    profit = 14090
                ),
                Statistic(
                    date = 1614577752,
                    name = "Lyft",
                    profitPercent = -14,
                    profit = -8090
                ),
                Statistic(
                    date = 1614577752,
                    name = "Pager Duty",
                    profitPercent = 0,
                    profit = 0
                ),
                Statistic(
                    date = 1614577752,
                    name = "Zoom",
                    profitPercent = 174,
                    profit = 481082
                ),
                Statistic(
                    date = 1614577752,
                    name = "RealReal",
                    profitPercent = -21,
                    profit = -81774
                ),
                Statistic(
                    date = 1614577752,
                    name = "Health Catalyst",
                    profitPercent = 68,
                    profit = 40090
                ),
                Statistic(
                    date = 1614577752,
                    name = "DataDog",
                    profitPercent = 41,
                    profit = 14045
                ),
                Statistic(
                    date = 1614577752,
                    name = "Buble",
                    profitPercent = 74,
                    profit = 78090
                ),
                Statistic(
                    date = 1614577752,
                    name = "Nike",
                    profitPercent = 67,
                    profit = 1600090
                ),
                Statistic(
                    date = 1614577752,
                    name = "British Petrolium",
                    profitPercent = -28,
                    profit = 17090
                )
            )
        )
    )

}