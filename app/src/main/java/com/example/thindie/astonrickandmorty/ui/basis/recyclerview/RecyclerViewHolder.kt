package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.MinorComponent
import com.example.thindie.astonrickandmorty.ui.uiutils.widGet
import kotlinx.coroutines.Dispatchers

class UiHolder(private val view: View) : RecyclerView.ViewHolder(view),
    SearchAbleAdapted {

    override fun getTitle(id: Int): TextView {
        return view.widGet(id)
    }

    override fun getHeadline(id: Int): TextView {
        return view.widGet(id)
    }

    override fun getBody(id: Int): TextView {
        return view.widGet(id)
    }

    override fun getExpandedBody(id: Int, isExpanded: Boolean): TextView? {
        return if (isExpanded) view.widGet(id)
        else null
    }

    override fun getImage(id: Int, isHasImage: Boolean): ImageView? {
        return if (isHasImage) view.widGet(id)
        else null
    }


}

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