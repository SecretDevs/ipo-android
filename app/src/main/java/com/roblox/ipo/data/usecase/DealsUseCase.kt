package com.roblox.ipo.data.usecase

import com.roblox.ipo.vo.inapp.Deal
import com.roblox.ipo.vo.inapp.Result
import javax.inject.Inject

interface DealsUseCase {
    fun getFavoriteDeals(): Result<List<Deal>>
    fun getIpoDeals(): Result<List<Deal>>
    fun getSpacDeals(): Result<List<Deal>>
    fun getStocksDeals(): Result<List<Deal>>
    fun getDealsStates(): Result<List<Boolean>>
    fun updateFave(dealId: Long): Result<List<Deal>>
}

class FakeDealsUseCase @Inject constructor() : DealsUseCase {
    override fun getFavoriteDeals(): Result<List<Deal>> = Result.Success(
        listOf(
            Deal(
                id = 13553,
                name = "Bumble",
                nameTraded = "BMBL",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 2,
                risk = 3,
                isFavorite = true,
                date = 1611249615
            ),
            Deal(
                id = 13552,
                name = "MYT Netherlands",
                nameTraded = "MYTE",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 1,
                risk = 2,
                isFavorite = true,
                date = 1611249615
            ),
            Deal(
                id = 13551,
                name = "TELUS International",
                nameTraded = "BMBL",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 1,
                risk = 1,
                isFavorite = true,
                date = 1611249615
            ),
            Deal(
                id = 13550,
                name = "ON24",
                nameTraded = "ONTF",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 3,
                risk = 2,
                isFavorite = true,
                date = 1611249615
            )
        )
    ).also { Thread.sleep(3000L) }

    override fun getIpoDeals(): Result<List<Deal>> = Result.Success(
        listOf(
            Deal(
                id = 13551,
                name = "TELUS International",
                nameTraded = "BMBL",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 1,
                risk = 1,
                isFavorite = false,
                date = 1611249615
            ),
            Deal(
                id = 13550,
                name = "ON24",
                nameTraded = "ONTF",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 3,
                risk = 2,
                isFavorite = true,
                date = 1611249615
            ),
            Deal(
                id = 13553,
                name = "Bumble",
                nameTraded = "BMBL",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 2,
                risk = 3,
                isFavorite = false,
                date = 1611249615
            )
        )
    ).also { Thread.sleep(3000L) }

    override fun getSpacDeals(): Result<List<Deal>> = Result.Success(
        listOf(
            Deal(
                id = 13550,
                name = "ON24",
                nameTraded = "ONTF",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 3,
                risk = 2,
                isFavorite = true,
                date = 1611249615
            ),
            Deal(
                id = 13551,
                name = "TELUS International",
                nameTraded = "BMBL",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliquaLorem ipsum dolor sit amet, cons ectetur adipiscing elit, sed do eiusm",
                state = 1,
                risk = 1,
                isFavorite = true,
                date = 1611249615
            )
        )
    ).also { Thread.sleep(3000L) }

    override fun getStocksDeals(): Result<List<Deal>> = Result.Success(
        emptyList<Deal>()
    ).also { Thread.sleep(3000L) }

    override fun getDealsStates(): Result<List<Boolean>> =
        Result.Success(
            listOf(true, true, true, false)
        ).also { Thread.sleep(3000L) }

    override fun updateFave(dealId: Long): Result<List<Deal>> = Result.Success(emptyList())

}