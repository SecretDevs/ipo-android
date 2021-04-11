package com.roblox.ipo.deals.ipo

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
class IpoDealsFragment : BaseFragment<IpoDealsViewState, IpoDealsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_deals_list
    override val viewModel: IpoDealsViewModel by viewModels()

    private lateinit var adapter: DealsAdapter

    override fun backStackIntent(): IpoDealsIntent =
        IpoDealsIntent.FavoriteDealsNothingIntent

    override fun initialIntent(): IpoDealsIntent =
        IpoDealsIntent.InitialDealsLoadingIntent

    override fun initViews() {
        adapter = DealsAdapter(
            onClick = { _intentLiveData.value = IpoDealsIntent.OpenDealDetailsIntent(it) },
            onFaveClick = { id, state ->
                _intentLiveData.value = IpoDealsIntent.ToggleDealFaveIntent(id, state)
            },
            onRetry = { _intentLiveData.value = IpoDealsIntent.RetryDealsLoadingIntent },
            onPagingRetry = { _intentLiveData.value = IpoDealsIntent.RetryPagingDealsLoadingIntent }
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
        deals_recycler.addOnScrollListener(OffsetScrollListener(
            deals_recycler.layoutManager as LinearLayoutManager,
            3,
            { _intentLiveData.value = IpoDealsIntent.PagingDealsLoadingIntent }
        ))
    }

    override fun render(viewState: IpoDealsViewState) {
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
        fun newInstance() = IpoDealsFragment()
    }

}