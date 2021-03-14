package com.roblox.ipo.deals.favorite

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import com.roblox.ipo.base.recycler.RecyclerState
import com.roblox.ipo.deals.recycler.FavoriteDealsAdapter
import com.roblox.ipo.deals.recycler.FavoriteDealsItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_deals_list.*

@AndroidEntryPoint
class FavoriteDealsFragment : BaseFragment<FavoriteDealsViewState, FavoriteDealsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_deals_list
    override val viewModel: FavoriteDealsViewModel by viewModels()

    private lateinit var adapter: FavoriteDealsAdapter

    override fun backStackIntent(): FavoriteDealsIntent =
        FavoriteDealsIntent.FavoriteDealsNothingIntent

    override fun initialIntent(): FavoriteDealsIntent =
        FavoriteDealsIntent.InitialDealsLoadingIntent

    override fun initViews() {
        adapter = FavoriteDealsAdapter(
            onClick = { _intentLiveData.value = FavoriteDealsIntent.OpenDealDetailsIntent(it) },
            onFaveClick = { _intentLiveData.value = FavoriteDealsIntent.ToggleDealFaveIntent(it) },
            onRetry = { _intentLiveData.value = FavoriteDealsIntent.RetryDealsLoadingIntent }
        )
        deals_recycler.adapter = adapter
        deals_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        deals_recycler.addItemDecoration(
            FavoriteDealsItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin_default
                )
            )
        )
    }

    override fun render(viewState: FavoriteDealsViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.deals.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        adapter.updateData(viewState.deals, state)
    }

    companion object {
        fun newInstance() = FavoriteDealsFragment()
    }

}