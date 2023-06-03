package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

data class ViewHolderIdSupplier(
    @LayoutRes val viewHolderLayout: Int,
    @IdRes val majorChild: Int,
    @IdRes val titleChild: Int,
    @IdRes val lesserChild: Int,
    @IdRes val expandedChild: Int? = null,
    @IdRes val imageChild: Int? = null,
)