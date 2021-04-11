package com.roblox.ipo.deals.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DealsItemDecoration(
    private val margin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (view.tag == "item") {
            outRect.set(
                margin,
                margin / 2,
                margin,
                margin / 2,
            )
        }
    }

}