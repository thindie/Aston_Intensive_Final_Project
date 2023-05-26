package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.operationable.OperationAbleAdapted
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview.RecyclerViewAdapterManager

interface RecyclerMediatorScrollManager<Model, VH> : RecyclerViewAdapterManager<Model, VH>
    where VH : RecyclerView.ViewHolder,
          VH : OperationAbleAdapted,
          Model : OperationAble {

    fun observe(eventStateListener: EventMediator<Scroll>)
}
