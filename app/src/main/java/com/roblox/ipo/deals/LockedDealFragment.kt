package com.roblox.ipo.deals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.roblox.ipo.R
import kotlinx.android.synthetic.main.fragment_locked_section.*

class LockedDealFragment : Fragment() {

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
            R.string.locked_deal_title, arguments?.getInt(
                DEAL_NUMBER
            )
        )
        locked_deal_buy_btn.text = resources.getString(
            R.string.locked_deal_buy_text, arguments?.getInt(
                DEAL_SUB_PRICE
            )
        )
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