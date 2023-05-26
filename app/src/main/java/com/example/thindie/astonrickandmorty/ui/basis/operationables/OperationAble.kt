package com.example.thindie.astonrickandmorty.ui.basis.operationables

import android.graphics.Bitmap
import android.widget.ImageView

interface OperationAble {
    fun getSearchingShadow(): String {
        return getMajorComponent()
            .plus(getMajorComponent1())
            .plus(getMajorComponent2())
    }

    fun <T : OperationAble> getReified(): T
    fun getMajorComponent(): String
    fun getMajorComponent1(): String
    fun getMajorComponent2(): String
    fun getMinorComponent(): MinorComponent
    fun getMinorComponent1(): MinorComponent
    fun hasMinorComponents(): Boolean
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
