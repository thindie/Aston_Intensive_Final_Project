package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import androidx.annotation.IdRes
import coil.load
import com.example.thindie.astonrickandmorty.ui.basis.operationables.MinorComponent
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.operationable.OperationAbleAdapted
import kotlinx.coroutines.Dispatchers

fun OperationAbleAdapted.activateMinorComponent(
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

fun OperationAbleAdapted.setText(
    stringHolderMinorComponent: MinorComponent.SimpleStringHolder,
    @IdRes expandedChild: Int
) {
    getExpandedBody(
        expandedChild,
        true
    )!!.text = stringHolderMinorComponent.string
}

fun OperationAbleAdapted.setImage(
    minorImageUrlComponent: MinorComponent.ImageUrlHolder,
    @IdRes imageId: Int
) {
    minorImageUrlComponent
        .extract({
            requireNotNull(
                getImage(
                    imageId,
                    true
                )
            )
        }) {
            load(it) {
                transformationDispatcher(Dispatchers.IO)
                allowConversionToBitmap(true)
            }
        }
}
