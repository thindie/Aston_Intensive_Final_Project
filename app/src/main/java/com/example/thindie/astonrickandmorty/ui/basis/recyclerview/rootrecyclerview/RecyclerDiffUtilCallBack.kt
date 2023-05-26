package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble

class RecyclerDiffUtilCallBack<Model : OperationAble> : DiffUtil.ItemCallback<Model>() {
    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
        val oldReified = oldItem.getReified<Model>()
        val newReified = newItem.getReified<Model>()
        return oldReified == newReified
    }

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
        val oldReified = oldItem.getReified<Model>()
        val newReified = newItem.getReified<Model>()
        return oldReified.getMajorComponent() == newReified.getMajorComponent()
    }
}
