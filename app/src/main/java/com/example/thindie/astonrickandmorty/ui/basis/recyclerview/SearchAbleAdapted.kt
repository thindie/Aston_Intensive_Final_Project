package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes

interface SearchAbleAdapted {
    fun getTitle(@IdRes id: Int): TextView
    fun getHeadline(@IdRes id: Int): TextView
    fun getBody(@IdRes id: Int): TextView
    fun getExpandedBody(@IdRes id: Int, isExpanded: Boolean): TextView?
    fun getImage(@IdRes id: Int, isHasImage: Boolean): ImageView?
}
