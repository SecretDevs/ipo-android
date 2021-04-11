package com.roblox.ipo.deals.recycler

import android.text.format.DateUtils
import android.view.View
import androidx.core.content.ContextCompat
import com.roblox.ipo.R
import com.roblox.ipo.base.recycler.DataViewHolder
import com.roblox.ipo.vo.inapp.Deal
import kotlinx.android.synthetic.main.item_deal.view.*

class DealViewHolder(
    private val root: View,
    onClick: (String) -> Unit,
    onFaveClick: (String, Boolean) -> Unit
) : DataViewHolder<Deal>(root) {
    private lateinit var deal: Deal

    init {
        root.setOnClickListener { onClick(deal.id ?: "") }
        root.item_deal_favorite_btn.setOnCheckedChangeListener { _, isChecked ->
            onFaveClick(
                deal.id ?: "",
                isChecked
            )
        }
    }

    override fun bindData(data: Deal) {
        this.deal = data
        root.item_deal_number.text = root.resources.getString(
            R.string.text_deal_number,
            data.id
        )
        root.item_deal_name.text = data.name
        root.item_deal_name_traded.text = data.nameTraded
        root.item_deal_description.text = data.description
        root.item_deal_date.text = root.resources.getString(
            R.string.text_deal_date,
            DateUtils.formatDateTime(
                root.context,
                data.createdTime,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NUMERIC_DATE
            ),
            DateUtils.formatDateTime(
                root.context,
                data.createdTime,
                DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_NUMERIC_DATE
            )
        )
        root.item_deal_favorite_btn.isChecked = data.isFavorite

        when (data.state) {
            1 -> {
                root.item_deal_state.text = root.resources.getString(R.string.text_chip_open_deal)
                root.item_deal_state.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.colorChipTextDealOpen
                    )
                )
                root.item_deal_state.setChipBackgroundColorResource(R.color.colorChipBackgroundDealOpen)
            }
            2 -> {
                root.item_deal_state.text =
                    root.resources.getString(R.string.text_chip_averaged_deal)
                root.item_deal_state.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.colorChipTextDealAveraged
                    )
                )
                root.item_deal_state.setChipBackgroundColorResource(R.color.colorChipBackgroundDealAveraged)
            }
            3 -> {
                root.item_deal_state.text = root.resources.getString(R.string.text_chip_closed_deal)
                root.item_deal_state.setTextColor(
                    ContextCompat.getColor(
                        root.context,
                        R.color.colorChipTextDealClosed
                    )
                )
                root.item_deal_state.setChipBackgroundColorResource(R.color.colorChipBackgroundDealClosed)
            }
        }
        root.item_deal_risk_value.text = root.resources.getString(
            when (data.risk) {
                1 -> R.string.text_low_risk_deal
                2 -> R.string.text_medium_risk_deal
                else -> R.string.text_high_risk_deal
            }
        )
        root.item_deal_risk_value.setCompoundDrawablesRelativeWithIntrinsicBounds(
            when (data.risk) {
                1 -> R.drawable.ic_low_risk
                2 -> R.drawable.ic_medium_risk
                else -> R.drawable.ic_high_risk
            },
            0, 0, 0
        )
    }

}