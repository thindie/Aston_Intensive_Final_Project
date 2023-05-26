package com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.core

import com.google.android.material.bottomsheet.BottomSheetBehavior

data class BottomSheetModel(
    val title: String,
    val state: Int = BottomSheetBehavior.STATE_HALF_EXPANDED
)
