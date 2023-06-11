package com.example.thindie.astonrickandmorty.ui.basis.uiApi

import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier

interface UsesSearchAbleAdaptedRecycleViewAdapter {

    val recyclerView: RecyclerView
    fun getHolderIdSupplier(): ViewHolderIdSupplier
    fun setRecyclerView()
    fun observeRecyclerView()

}