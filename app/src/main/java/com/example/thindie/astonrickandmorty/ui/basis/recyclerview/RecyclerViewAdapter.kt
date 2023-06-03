@file:Suppress("UNCHECKED_CAST")

package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble

class RecyclerViewAdapter<Model : SearchAble, ViewHolder>
constructor(private val viewHolderIdSupplier: ViewHolderIdSupplier) :
    ListAdapter<Model, ViewHolder>(RecyclerDiffUtilCallBack<Model>())
        where ViewHolder : RecyclerView.ViewHolder, ViewHolder : SearchAbleAdapted {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return UiHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewHolderIdSupplier.viewHolderLayout, parent, false)
        ) as ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.getHeadline(viewHolderIdSupplier.majorChild).text = item.getMajorComponent()
        holder.getTitle(viewHolderIdSupplier.titleChild).text = item.getMajorComponent1()
        holder.getBody(viewHolderIdSupplier.lesserChild).text = item.getMajorComponent2()
        val isExpanded = item.hasMinorComponents()
        if (isExpanded) {
            holder.activateMinorComponent(
                item.getMinorComponent(),
                viewHolderIdSupplier.imageChild!!,
                viewHolderIdSupplier.expandedChild!!
            )
            holder.activateMinorComponent(
                item.getMinorComponent1(),
                viewHolderIdSupplier.imageChild,
                viewHolderIdSupplier.expandedChild
            )
        }
    }
}


class RecyclerDiffUtilCallBack<Model : SearchAble> : DiffUtil.ItemCallback<Model>() {
    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
        val oldItemLanded = oldItem.getReified<Model>()
        val newItemLanded = newItem.getReified<Model>()
        return oldItemLanded == newItemLanded
    }

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
        return false
    }
}






