package com.example.thindie.astonrickandmorty.ui.basis.uiApi

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.Scroll

class ScrollListener(private val listener: EventMediator<Scroll>) :
    RecyclerView.OnScrollListener() {
    private var onFirstTimeScrollEvent = true
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!recyclerView.canScrollVertically(1)) {
            listener.onEvent(Scroll.BOTTOM)
        }
        if (!recyclerView.canScrollVertically(-1)) {
            if (!onFirstTimeScrollEvent) {
                listener.onEvent(Scroll.TOP)
            }
            onFirstTimeScrollEvent = false
        }
    }
}
