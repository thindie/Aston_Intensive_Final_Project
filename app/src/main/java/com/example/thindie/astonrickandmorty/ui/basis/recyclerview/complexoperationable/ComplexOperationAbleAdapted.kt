package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable

import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.operationable.OperationAbleAdapted

interface ComplexOperationAbleAdapted : OperationAbleAdapted {
    fun getRecyclerView(@IdRes id: Int): RecyclerView
}
