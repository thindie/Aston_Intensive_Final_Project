@file:Suppress("UNCHECKED_CAST")

package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.operationable.OperationAbleAdapted
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview.RecyclerViewAdapter

open class RecyclerViewAdapterMediatorScroll<Model : OperationAble, ViewHolder>
constructor(viewHolderIdSupplier: ViewHolderIdSupplier, onClickedViewHolder: (Model) -> Unit) :
    RecyclerViewAdapter<Model, ViewHolder>(viewHolderIdSupplier, onClickedViewHolder),
    EventMediator<Scroll>
        where ViewHolder : RecyclerView.ViewHolder, ViewHolder : OperationAbleAdapted {

    override var event: ((Scroll) -> Unit)? = null
    override fun onEvent(t: Scroll) {
        event?.invoke(t)
    }
}











