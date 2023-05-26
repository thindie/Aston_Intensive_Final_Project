package com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet

import android.widget.TextView
import androidx.annotation.IdRes
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable.ComplexOperationAbleAdapted

interface BottomSheetAdapted : ComplexOperationAbleAdapted {
    fun getExtraBottomSheetChild(@IdRes id: Int): TextView?
}
