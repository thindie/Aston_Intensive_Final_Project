package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import com.example.thindie.astonrickandmorty.ui.basis.operationables.ComplexOperationAble
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.activateMinorComponent
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview.UiHolder
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.Scroll

class ComplexedRecyclerViewAdapter<Model, ViewHolder>
constructor(
    private val viewHolderIdSupplier: ViewHolderIdSupplier,
    private val slaveViewHolderIdSupplier: ViewHolderIdSupplier,
    private val onClickedSlaveViewHolder: (OperationAble) -> Unit,
    private val onClickedViewHolder: (Model) -> Unit
) : ListAdapter<Model, ViewHolder>(ComplexDiffUtilCallBack()), EventMediator<Scroll>
    where ViewHolder : RecyclerView.ViewHolder, ViewHolder : ComplexOperationAbleAdapted,
          Model : ComplexOperationAble {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ComplexUiHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewHolderIdSupplier.viewHolderLayout, parent, false)
        ) as ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        lateinit var adapter: RecyclerViewAdapter<OperationAble, UiHolder>
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

        if (item.isComplexComponentNeeded()) {
            val recyclerView: RecyclerView =
                holder.getRecyclerView(viewHolderIdSupplier.extraChild!!)
            adapter = RecyclerViewAdapter(slaveViewHolderIdSupplier) {
                onClickedSlaveViewHolder(it)
            }
            recyclerView.adapter = adapter
            adapter.submitList(item.getComplexComponent())

            recyclerView.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
                }
            })
        }

        holder.itemView.setOnClickListener {
            onClickedViewHolder(item)
        }
    }

    override var event: ((Scroll) -> Unit)? = null

    override fun onEvent(t: Scroll) {
        event?.invoke(t)
    }
}
