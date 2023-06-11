@file:Suppress("UNCHECKED_CAST")

package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecyclerViewAdapter<Model : SearchAble, ViewHolder>
constructor(private val viewHolderIdSupplier: ViewHolderIdSupplier) :
    ListAdapter<Model, ViewHolder>(RecyclerDiffUtilCallBack<Model>()),
    EventMediator<Scroll>
        where ViewHolder : RecyclerView.ViewHolder, ViewHolder : SearchAbleAdapted {

    private val _eventFlow: MutableStateFlow<Scroll> = MutableStateFlow(Scroll.STILL)
    override val eventFlow = _eventFlow.asStateFlow()

    private var isActiveAsScrollListener = statusActive
    override fun isActive(): Boolean {
        return isActiveAsScrollListener == statusActive
    }

    override fun setStatus(status: Int) {
        when (status) {
            0 -> isActiveAsScrollListener = statusTurnedOff
            1 -> isActiveAsScrollListener = statusActive
            else -> { /* ignore */ }
        }
    }

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


    override fun onEvent() {
        Log.d("SERVICE_TAG", "triggered_event")

        //todo
        _eventFlow.value = Scroll.BOTTOM
        _eventFlow.value = Scroll.STILL
    }

}

enum class Scroll {
    STILL, BOTTOM, TOP
}


class RecyclerDiffUtilCallBack<Model : SearchAble> : DiffUtil.ItemCallback<Model>() {
    override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
        val oldItemLanded = oldItem.getReified<Model>()
        val newItemLanded = newItem.getReified<Model>()
        return oldItemLanded == newItemLanded
    }

    override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
        val oldItemLanded = oldItem.getReified<Model>()
        val newItemLanded = newItem.getReified<Model>()
        return oldItemLanded.getMajorComponent() == newItemLanded.getMajorComponent()
    }
}







