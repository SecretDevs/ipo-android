package com.roblox.ipo.deals.favorite

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import com.roblox.ipo.base.recycler.RecyclerEndlessState
import com.roblox.ipo.deals.recycler.DealsAdapter
import com.roblox.ipo.deals.recycler.DealsItemDecoration
import com.roblox.ipo.deals.recycler.OffsetScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_deals_list.*

@AndroidEntryPoint
class FavoriteDealsFragment : BaseFragment<FavoriteDealsViewState, FavoriteDealsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_deals_list
    override val viewModel: FavoriteDealsViewModel by viewModels()

    private lateinit var adapter: DealsAdapter

    override fun backStackIntent(): FavoriteDealsIntent =
        FavoriteDealsIntent.FavoriteDealsNothingIntent

    override fun initialIntent(): FavoriteDealsIntent =
        FavoriteDealsIntent.InitialDealsLoadingIntent

    override fun initViews() {
        adapter = DealsAdapter(
            onClick = { _intentLiveData.value = FavoriteDealsIntent.OpenDealDetailsIntent(it) },
            onFaveClick = { id, state ->
                _intentLiveData.value = FavoriteDealsIntent.ToggleDealFaveIntent(id, state) },
            onRetry = { _intentLiveData.value = FavoriteDealsIntent.RetryDealsLoadingIntent },
            onPagingRetry = {
                _intentLiveData.value = FavoriteDealsIntent.RetryPagingDealsLoadingIntent
            }
        )
        deals_recycler.adapter = adapter
        deals_recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        deals_recycler.addItemDecoration(
            DealsItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin_default
                )
            )
        )
        deals_recycler.addOnScrollListener(
            OffsetScrollListener(
                mLayoutManager = deals_recycler.layoutManager as LinearLayoutManager,
                mOffset = 3,
                offsetListener = {
                    _intentLiveData.value = FavoriteDealsIntent.PagingDealsLoadingIntent
                }
            )
        )
    }

    override fun render(viewState: FavoriteDealsViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerEndlessState.LOADING
            viewState.initialError != null -> RecyclerEndlessState.ERROR
            viewState.isPagingLoading -> RecyclerEndlessState.PAGING_LOADING
            viewState.pagingError != null -> RecyclerEndlessState.PAGING_ERROR
            viewState.deals.isEmpty() -> RecyclerEndlessState.EMPTY
            else -> RecyclerEndlessState.ITEM
        }
        adapter.updateData(viewState.deals, state)
    }

    companion object {
        fun newInstance() = FavoriteDealsFragment()
    }

}