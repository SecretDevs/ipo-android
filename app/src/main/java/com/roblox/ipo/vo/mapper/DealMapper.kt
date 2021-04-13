package com.roblox.ipo.vo.mapper

import com.roblox.ipo.utils.DateConverter
import com.roblox.ipo.vo.inapp.Deal
import com.roblox.ipo.vo.remote.RemoteDeal
import javax.inject.Inject

class DealMapper @Inject constructor(
    private val dateConverter: DateConverter
) : Mapper<Deal, RemoteDeal> {
    override fun fromInappToRemote(data: Deal): RemoteDeal =
        RemoteDeal(
            id = data.id,
            name = data.name,
            ticker = data.nameTraded,
            description = data.description,
            createdAt = dateConverter.fromTimestampToIsoString(data.createdTime) ?: "",
            updatedAt = dateConverter.fromTimestampToIsoString(data.updatedTime),
            isFavourite = data.isFavorite,
            risk = riskInappMapper[data.risk] ?: "Низкий",
            status = statusInappMapper[data.state] ?: "opened"
        )

    override fun fromRemoteToInapp(data: RemoteDeal): Deal =
        Deal(
            id = data.id ?: "",
            name = data.name,
            nameTraded = data.ticker,
            description = data.description,
            createdTime = dateConverter.fromIsoStringToTimestamp(data.createdAt) ?: 0L,
            updatedTime = dateConverter.fromIsoStringToTimestamp(data.updatedAt),
            isFavorite = data.isFavourite ?: false,
            risk = riskRemoteMapper[data.risk] ?: 1,
            state = statusRemoteMapper[data.status] ?: 1
        )

    companion object {
        private val riskRemoteMapper = mapOf(
            "Низкий" to 1,
            "Средний" to 2,
            "Высокий" to 3
        )
        private val riskInappMapper = mapOf(
            1 to "Низкий",
            2 to "Средний",
            3 to "Высокий"
        )
        private val statusRemoteMapper = mapOf(
            "opened" to 1,
            "averaged" to 2,
            "closed" to 3
        )
        private val statusInappMapper = mapOf(
            1 to "opened",
            2 to "averaged",
            3 to "closed"
        )
    }

}