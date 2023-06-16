package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.widGet

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

    override fun getFragmentContainer(@IdRes id: Int, context: Context): FragmentContainerView? {
        return  view.widGet(id)
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

