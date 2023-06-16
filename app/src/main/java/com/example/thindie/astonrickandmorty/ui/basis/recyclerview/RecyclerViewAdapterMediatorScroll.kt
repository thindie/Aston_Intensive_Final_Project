@file:Suppress("UNCHECKED_CAST")

package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

open class RecyclerViewAdapterMediatorScroll<Model : SearchAble, ViewHolder>
constructor(viewHolderIdSupplier: ViewHolderIdSupplier, onClickedViewHolder: (Model) -> Unit) :
    RecyclerViewAdapter<Model, ViewHolder>(viewHolderIdSupplier, onClickedViewHolder),
    EventMediator<Scroll>
        where ViewHolder : RecyclerView.ViewHolder, ViewHolder : SearchAbleAdapted {


    override var event: ((Scroll) ->  Unit)? = null


    override fun onEvent(t: Scroll) {
         event?.invoke(t)
    }

}

enum class Scroll {
    STILL, BOTTOM, TOP
}









