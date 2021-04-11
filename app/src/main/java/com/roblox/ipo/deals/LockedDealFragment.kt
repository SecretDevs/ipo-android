package com.roblox.ipo.deals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import com.roblox.ipo.navigation.Coordinator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_locked_section.*
import javax.inject.Inject

@AndroidEntryPoint
class LockedDealFragment : Fragment() {
    @Inject
    lateinit var coordinator: Coordinator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_locked_section,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locked_deal_title.text = resources.getString(
            R.string.title_locked_deal,
            when (arguments?.getInt(DEAL_NUMBER)) {
                1 -> resources.getString(R.string.title_locked_deal_ipo)
                2 -> resources.getString(R.string.title_locked_deal_spac)
                3 -> resources.getString(R.string.title_locked_deal_stocks)
                else -> throw IllegalArgumentException("too much tabs")
            }
        )
        locked_deal_buy_price.text = resources.getString(
            R.string.text_locked_deal_buy_price,
            arguments?.getInt(DEAL_SUB_PRICE)
        )
        locked_deal_buy_btn.setOnClickListener {
            coordinator.navigateToPayment(1, arguments?.getInt(DEAL_SUB_PRICE) ?: 0)
        }
        locked_deal_statistic_btn.setOnClickListener {
            coordinator.navigateToStatistic()
        }
    }

    companion object {
        fun newInstance(position: Int, price: Int) = LockedDealFragment().also {
            it.arguments = bundleOf(
                DEAL_NUMBER to position,
                DEAL_SUB_PRICE to price
            )
        }

        private const val DEAL_NUMBER = "DEAL_NUMBER_KEY"
        private const val DEAL_SUB_PRICE = "DEAL_SUBSCRIPTION_KEY"
    }

}