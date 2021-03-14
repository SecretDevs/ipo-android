package com.roblox.ipo.deals.stocks

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import com.roblox.ipo.base.recycler.RecyclerState
import com.roblox.ipo.deals.recycler.DealsAdapter
import com.roblox.ipo.deals.recycler.DealsItemDecoration
import com.roblox.ipo.deals.spac.SpacDealsIntent
import com.roblox.ipo.deals.spac.SpacDealsViewModel
import com.roblox.ipo.deals.spac.SpacDealsViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_deals_list.*

@AndroidEntryPoint
class StockDealsFragment : BaseFragment<SpacDealsViewState, SpacDealsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_deals_list
    override val viewModel: SpacDealsViewModel by viewModels()

    private lateinit var adapter: DealsAdapter

    override fun backStackIntent(): SpacDealsIntent =
        SpacDealsIntent.FavoriteDealsNothingIntent

    override fun initialIntent(): SpacDealsIntent =
        SpacDealsIntent.InitialDealsLoadingIntent

    override fun initViews() {
        adapter = DealsAdapter(
            onClick = { _intentLiveData.value = SpacDealsIntent.OpenDealDetailsIntent(it) },
            onFaveClick = { _intentLiveData.value = SpacDealsIntent.ToggleDealFaveIntent(it) },
            onRetry = { _intentLiveData.value = SpacDealsIntent.RetryDealsLoadingIntent }
        )
        deals_recycler.adapter = adapter
        deals_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        deals_recycler.addItemDecoration(
            DealsItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin_default
                ),
                resources.getDimensionPixelSize(
                    R.dimen.fab_size
                )
            )
        )
    }

    override fun render(viewState: SpacDealsViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.deals.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        adapter.updateData(viewState.deals, state)
    }

    companion object {
        fun newInstance() = StockDealsFragment()
    }

}