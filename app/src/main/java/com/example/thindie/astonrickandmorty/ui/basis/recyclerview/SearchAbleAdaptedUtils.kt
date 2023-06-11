package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import androidx.annotation.IdRes
import coil.load
import com.example.thindie.astonrickandmorty.ui.uiutils.MinorComponent
import kotlinx.coroutines.Dispatchers

fun SearchAbleAdapted.activateMinorComponent(
    minorComponent: MinorComponent,
    @IdRes minorComponentImageCase: Int,
    @IdRes minorComponentStringCase: Int,
) {
    when (minorComponent) {
        is MinorComponent.ImageUrlHolder -> {
            setImage(minorComponent, minorComponentImageCase)
        }
        is MinorComponent.SimpleStringHolder -> {
            setText(minorComponent, minorComponentStringCase)
        }
    }

}

fun SearchAbleAdapted.setText(
    stringHolderMinorComponent: MinorComponent.SimpleStringHolder,
    @IdRes expandedChild: Int
) {
    getExpandedBody(
        expandedChild,
        true
    )!!.text = stringHolderMinorComponent.string
}

fun SearchAbleAdapted.setImage(
    minorImageUrlComponent: MinorComponent.ImageUrlHolder,
    @IdRes imageId: Int
) {
    minorImageUrlComponent
        .extract({
            requireNotNull(
                getImage(
                    imageId, true
                )
            )
        }) {
            load(it) {
                transformationDispatcher(Dispatchers.IO)
                allowConversionToBitmap(true)
            }
        }
}