package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.operationables.ComplexOperationAble

interface ComplexRecyclerViewAdapterManager<Model, VH>

    where VH : RecyclerView.ViewHolder,
          VH : ComplexOperationAbleAdapted,
          Model : ComplexOperationAble {

    fun <T : ComplexedRecyclerViewAdapter<Model, VH>> getAdapter(): T
    fun <T : ComplexedRecyclerViewAdapter<Model, VH>> setAdapter(adapter: T)

    fun submitList(modelList: List<Model>)
}
