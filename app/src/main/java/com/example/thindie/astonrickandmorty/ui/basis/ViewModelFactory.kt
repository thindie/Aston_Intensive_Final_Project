package com.example.thindie.astonrickandmorty.ui.basis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory
@Inject constructor(private val storedViewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<@JvmSuppressWildcards T>): T {
        return requireNotNull(storedViewModels[modelClass]) {
            " no such stored VM"
        }.get() as T

    }
}