package com.roblox.ipo.deals.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FavoriteDealsItemDecoration(
    private val margin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        outRect.set(
            margin,
            margin / 2,
            margin,
            if (position == 0) margin else margin / 2,
        )
    }

}