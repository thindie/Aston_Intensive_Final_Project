package com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.core

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.BottomSheetAdapted
import com.example.thindie.astonrickandmorty.ui.uiutils.widGet

class BottomSheetViewHolder(val view: View) : RecyclerView.ViewHolder(view), BottomSheetAdapted {
    override fun getExtraBottomSheetChild(id: Int): TextView? {
        return view.widGet(id)
    }

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
        return if (isExpanded) view.widGet(id) else null
    }

    override fun getImage(id: Int, isHasImage: Boolean): ImageView? {
        return if (isHasImage) view.widGet(id) else null
    }
}
