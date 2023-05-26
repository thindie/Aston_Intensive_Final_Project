package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.operationable.OperationAbleAdapted

interface RecyclerViewAdapterManager<Model, VH>
    where VH : RecyclerView.ViewHolder,
          VH : OperationAbleAdapted,
          Model : OperationAble {
    fun <T : RecyclerViewAdapter<Model, VH>> getAdapter(): T
    fun <T : RecyclerViewAdapter<Model, VH>> setAdapter(adapter: T)

    fun submitList(modelList: List<Model>)
}
