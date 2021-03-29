package com.roblox.ipo.deals.ipo

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.roblox.ipo.R
import com.roblox.ipo.base.BaseFragment
import com.roblox.ipo.base.recycler.RecyclerState
import com.roblox.ipo.deals.recycler.DealsAdapter
import com.roblox.ipo.deals.recycler.DealsItemDecoration
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
            onFaveClick = { _intentLiveData.value = IpoDealsIntent.ToggleDealFaveIntent(it) },
            onRetry = { _intentLiveData.value = IpoDealsIntent.RetryDealsLoadingIntent }
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
    }

    override fun render(viewState: IpoDealsViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.deals.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        adapter.updateData(viewState.deals, state)
    }

    companion object {
        fun newInstance() = IpoDealsFragment()
    }

}