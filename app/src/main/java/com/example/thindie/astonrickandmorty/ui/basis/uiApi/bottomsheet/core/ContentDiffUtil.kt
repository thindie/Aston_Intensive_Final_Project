package com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.core

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.BottomSheetFiller

class ContentDiffUtil<T : BottomSheetFiller> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getComponent1() == newItem.getComponent1()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}
