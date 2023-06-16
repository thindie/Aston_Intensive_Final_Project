package com.example.thindie.astonrickandmorty.ui.uiutils

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngine
import com.google.android.material.textfield.TextInputEditText

interface SearchEngineManager {
    fun getSearchObserveAble(): SearchObservable
}

interface SearchEngineUser {
    fun getSearchingEngineManager(): SearchEngineManager
    fun getSearchingConsumer(): SearchEngineResultConsumer
    fun getSearchAbleList(): List<SearchAble>
    fun setEngine(engine: SearchEngine)

}

interface SearchEngineResultConsumer {
    fun onSearchResult(resultList: List<SearchAble>)
}

interface SearchAble {
    fun getSearchingShadow(): String {
        return getMajorComponent()
            .plus(getMajorComponent1())
            .plus(getMajorComponent2())
    }

    fun <T : SearchAble> getReified(): T
    fun getMajorComponent(): String
    fun getMajorComponent1(): String
    fun getMajorComponent2(): String

    fun getExtraComponent(): List<String>

    fun getMinorComponent(): MinorComponent
    fun getMinorComponent1(): MinorComponent
    fun hasMinorComponents(): Boolean
}

sealed class SearchObservable {
    data class WidgetHolder(val text: TextInputEditText) :
        SearchObservable()

    data class MutableStateHolder(
        val state: LiveData<String>,
        val lifecycleOwner: LifecycleOwner
    ) :
        SearchObservable()
}

sealed class MinorComponent {
    class ImageUrlHolder(private val url: String) : MinorComponent() {
        fun extract(
            imageViewProvider: () -> ImageView,
            imageViewImageInjector: ImageView.(String) -> Unit
        ) {
            imageViewProvider.invoke().imageViewImageInjector(url)
        }

        suspend fun extract(
            imageLoader: suspend (String) -> Bitmap
        ): Bitmap {
            return imageLoader(url)
        }
    }

    data class SimpleStringHolder(val string: String) : MinorComponent()
}