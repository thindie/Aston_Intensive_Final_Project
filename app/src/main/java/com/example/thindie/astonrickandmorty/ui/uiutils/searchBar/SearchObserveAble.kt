package com.example.thindie.astonrickandmorty.ui.uiutils.searchBar

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.textfield.TextInputLayout

sealed class SearchObserveAble {
    data class WidgetHolder(val text: TextInputLayout) :
        SearchObserveAble() {

        fun setClear(): WidgetHolder {
            text.editText?.setText("")
            return this
        }

        fun visibility(cause: Boolean): WidgetHolder {
            if (cause) {
                text.visibility = View.VISIBLE
            } else text.visibility = View.INVISIBLE
            return this
        }
    }

    data class MutableStateHolder(
        val state: LiveData<String>,
        val lifecycleOwner: LifecycleOwner,
    ) :
        SearchObserveAble()
}
