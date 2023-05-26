package com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.widGet

class ComplexUiHolder(private val view: View) :
    RecyclerView.ViewHolder(view),
    ComplexOperationAbleAdapted {
    override fun getRecyclerView(id: Int): RecyclerView {
        return view.widGet(id)
    }

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
        return view.widGet(id)
    }

    override fun getImage(id: Int, isHasImage: Boolean): ImageView? {
        return view.widGet(id)
    }
}
