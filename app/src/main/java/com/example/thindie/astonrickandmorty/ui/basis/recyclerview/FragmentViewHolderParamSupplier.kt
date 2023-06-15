package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import android.os.Bundle
import androidx.annotation.LayoutRes

data class FragmentViewHolderParamSupplier(
    @LayoutRes val fragmentContainer: Int,
)


data class FragmentInstanceBundleSupplier(
    val bundle: Bundle
) {
    fun get(): List<String> {
        return bundle.getStringArrayList(BUNDLE)!!.toList()
    }

    fun put() = bundle

    companion object {
        const val BUNDLE = "bundle"
    }
}

