package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

interface RecyclerViewAdapterManager<Model : SearchAble, VH, SlaveFragment, T>
        where VH : RecyclerView.ViewHolder, VH : SearchAbleAdapted {
    val adapter: RecyclerViewAdapter<Model, VH>
    fun <Adapter : RecyclerViewAdapterFragment<Model, VH, SlaveFragment>> setAdapter(adapter: Adapter)
    fun observe(eventStateListener: EventMediator<T>)
}