package com.roblox.ipo.deals.recycler

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import com.roblox.ipo.R
import com.roblox.ipo.base.recycler.DataViewHolder
import com.roblox.ipo.vo.inapp.IpoResult
import kotlinx.android.synthetic.main.item_portfolio.view.*
import java.text.SimpleDateFormat
import java.util.*

class StatisticItemViewHolder(
    private val root: View
) : DataViewHolder<IpoResult>(root) {

    init {
        if (formatter == null) {
            formatter = SimpleDateFormat(
                "dd/MM/yy",
                ConfigurationCompat.getLocales(root.resources.configuration)[0]
            )
        }
    }

    override fun bindData(data: IpoResult) {
        root.item_portfolio_date.text =
            formatter?.format(Date(data.date))
        root.item_portfolio_name.text = data.name
        root.item_portfolio_profit_percent.text = root.resources.getString(
            R.string.text_item_portfolio_profit_percent,
            data.profitPercent
        )
        root.item_portfolio_profit.text = root.resources.getString(
            R.string.text_item_portfolio_profit,
            data.profit
        )
        root.item_portfolio_profit_percent.setTextColor(
            when {
                data.profitPercent > 0 -> ContextCompat.getColor(root.context, R.color.colorGreen)
                data.profitPercent < 0 -> ContextCompat.getColor(root.context, R.color.colorRed)
                else -> ContextCompat.getColor(root.context, R.color.colorOnBackground)
            }
        )
    }

    companion object {
        private var formatter: SimpleDateFormat? = null
    }

}