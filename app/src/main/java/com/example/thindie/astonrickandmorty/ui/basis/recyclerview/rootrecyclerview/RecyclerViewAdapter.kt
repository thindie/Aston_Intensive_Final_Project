@file:Suppress("UNCHECKED_CAST")

package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.activateMinorComponent
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.operationable.OperationAbleAdapted

open class RecyclerViewAdapter<Model : OperationAble, ViewHolder>
constructor(
    private val viewHolderIdSupplier: ViewHolderIdSupplier,
    private val onClickedViewHolder: (Model) -> Unit
) :
    ListAdapter<Model, ViewHolder>(RecyclerDiffUtilCallBack<Model>())

    where ViewHolder : RecyclerView.ViewHolder, ViewHolder : OperationAbleAdapted {

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
            try {
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
            } catch (nullProperty: NullPointerException) {
            }
        }

        holder.itemView.setOnClickListener {
            onClickedViewHolder(item)
        }
    }
}
