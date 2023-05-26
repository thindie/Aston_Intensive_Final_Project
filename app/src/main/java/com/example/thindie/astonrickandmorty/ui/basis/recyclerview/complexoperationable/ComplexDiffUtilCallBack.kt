package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable

import androidx.recyclerview.widget.DiffUtil
import com.example.thindie.astonrickandmorty.ui.basis.operationables.ComplexOperationAble

class ComplexDiffUtilCallBack<Model : ComplexOperationAble> : DiffUtil.ItemCallback<Model>() {
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
