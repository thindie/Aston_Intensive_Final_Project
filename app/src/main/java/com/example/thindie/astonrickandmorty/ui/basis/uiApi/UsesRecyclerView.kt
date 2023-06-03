package com.example.thindie.astonrickandmorty.ui.basis.uiApi

import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier

interface UsesRecyclerView {
    fun getHolderIdSupplier(): ViewHolderIdSupplier
    fun setRecyclerView()
    fun observeRecyclerView()
}