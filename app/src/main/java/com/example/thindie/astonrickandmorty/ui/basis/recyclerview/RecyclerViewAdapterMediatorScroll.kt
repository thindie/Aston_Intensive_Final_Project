@file:Suppress("UNCHECKED_CAST")

package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecyclerViewAdapterMediatorScroll<Model : SearchAble, ViewHolder>
constructor(viewHolderIdSupplier: ViewHolderIdSupplier) :
    RecyclerViewAdapter<Model, ViewHolder>(viewHolderIdSupplier),
    EventMediator<Scroll>
        where ViewHolder : RecyclerView.ViewHolder, ViewHolder : SearchAbleAdapted {


    override var event: ((Scroll) ->  Unit)? = null


    override fun onEvent() {
         event?.invoke(Scroll.BOTTOM)
    }

}

enum class Scroll {
    STILL, BOTTOM, TOP
}









