package com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.BottomSheetAdapted
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.BottomSheetFiller
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.BottomSheetFillerExtended

class BottomSheetAdditionalListAdapter<T : BottomSheetFiller, R>(
    private val onClickAction: (T) -> Unit,
    private val supplier: ViewHolderIdSupplier,
) : ListAdapter<T, R>(ContentDiffUtil<T>()) where R : RecyclerView.ViewHolder, R : BottomSheetAdapted {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): R {
        return LayoutInflater.from(parent.context)
            .inflate(supplier.viewHolderLayout, parent, false) as R
    }

    override fun onBindViewHolder(holder: R, position: Int) {
        val model = getItem(position)
        model as BottomSheetFiller
        holder.getHeadline(supplier.majorChild).text = model.getComponent1()
        holder.getBody(supplier.lesserChild).text = model.getComponent2()

        try {
            model as BottomSheetFillerExtended
            holder.getExtraBottomSheetChild(supplier.expandedChild!!)?.text = model
                .getExtendedComponent()
            // model as BottomSheetExtra
        } catch (_: NullPointerException) {
        }
    }
}
