package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

interface RecyclerViewAdapterManager<Model : SearchAble, VH, T>
        where VH : RecyclerView.ViewHolder, VH : SearchAbleAdapted {
    val adapter: RecyclerViewAdapter<Model, VH>
    fun <Adapter : RecyclerViewAdapter<Model, VH>> setAdapter(adapter: Adapter)
    fun observe(eventStateListener: EventMediator<T>)
}