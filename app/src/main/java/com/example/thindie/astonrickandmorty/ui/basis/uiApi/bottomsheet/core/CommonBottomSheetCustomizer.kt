package com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.core

import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CommonBottomSheetCustomizer(
    private val bottomSheetModel: BottomSheetModel
) {

    fun customize(
        bottomSheetLayout: FrameLayout
    ) {
        val behaviour = BottomSheetBehavior.from(bottomSheetLayout)
        behaviour.state = bottomSheetModel.state
    }
}
