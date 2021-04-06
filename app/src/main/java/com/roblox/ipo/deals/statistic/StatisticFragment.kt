package com.roblox.ipo.deals.statistic

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import com.roblox.ipo.base.recycler.RecyclerState
import com.roblox.ipo.deals.recycler.StatisticAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistic.*
import kotlinx.android.synthetic.main.fragment_statistic_content.*

@AndroidEntryPoint
class StatisticFragment : BaseFragment<StatisticViewState, StatisticIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_statistic
    override val viewModel: StatisticViewModel by viewModels()

    private lateinit var adapter: StatisticAdapter

    override fun backStackIntent(): StatisticIntent = StatisticIntent.StatisticNothingIntent

    override fun initialIntent(): StatisticIntent = StatisticIntent.InitialIntent

    override fun initViews() {
        btn_arrow_back.setOnClickListener {
            _intentLiveData.value = StatisticIntent.BackArrowClickIntent
        }
        statistic_card_period.setOnCheckedChangeListener { group, checkedId ->
            _intentLiveData.value = StatisticIntent.ChangeChartRangeIntent(
                when (checkedId) {
                    R.id.statistic_card_period_day -> StatisticChartRange.DAY
                    R.id.statistic_card_period_week -> StatisticChartRange.WEEK
                    R.id.statistic_card_period_month -> StatisticChartRange.MONTH
                    R.id.statistic_card_period_year -> StatisticChartRange.YEAR
                    R.id.statistic_card_period_all -> StatisticChartRange.ALL
                    else -> throw IllegalStateException("unknown id choosen")
                }
            )
        }
        adapter = StatisticAdapter()
        statistic_detailed.adapter = adapter
        statistic_detailed.layoutManager = LinearLayoutManager(requireContext())
        statistic_card_chart.gradientFillColors = intArrayOf(
            Color.parseColor("#362E50CB"),
            Color.parseColor("#002E50CB"),
        )

    }

    override fun render(viewState: StatisticViewState) {
        statistic_card_period.check(
            when (viewState.currentChartRange) {
                StatisticChartRange.DAY -> R.id.statistic_card_period_day
                StatisticChartRange.WEEK -> R.id.statistic_card_period_week
                StatisticChartRange.MONTH -> R.id.statistic_card_period_month
                StatisticChartRange.YEAR -> R.id.statistic_card_period_year
                StatisticChartRange.ALL -> R.id.statistic_card_period_all
            }
        )
        val state = when {
            viewState.isLoading -> RecyclerState.LOADING
            viewState.errorLoading != null -> RecyclerState.ERROR
            viewState.data != null && viewState.data.portfolioItems.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        if (viewState.data != null) {
            statistic_card_chart.labelsFormatter = {
                if (
                    it == viewState.data.data.maxOf { x -> x }.toFloat() ||
                    it == viewState.data.data.minOf { x -> x }.toFloat()
                ) ""
                else "$${it.toInt()}"
            }

            statistic_card_growth.text = resources.getString(
                R.string.text_item_portfolio_profit_percent,
                viewState.data.profitPercent
            )
            statistic_card_growth.setTextColor(
                when {
                    viewState.data.profitPercent > 0 -> ContextCompat.getColor(
                        requireContext(),
                        R.color.colorGreen
                    )
                    viewState.data.profitPercent < 0 -> ContextCompat.getColor(
                        requireContext(),
                        R.color.colorRed
                    )
                    else -> ContextCompat.getColor(requireContext(), R.color.colorOnBackground)
                }
            )
            statistic_card_current_size.text = resources.getString(
                R.string.text_item_portfolio_profit,
                viewState.data.totalProfit
            )
            statistic_card_chart.show(viewState.data.data.map { "" to it.toFloat() })
            adapter.updateData(viewState.data.portfolioItems)
        }
    }
}