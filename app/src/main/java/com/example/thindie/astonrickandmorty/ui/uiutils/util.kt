@file:Suppress("Unchecked_cast")

package com.example.thindie.astonrickandmorty.ui.uiutils

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

const val ON_FAILED_FIND_VIEW_BY_ID = " nothing found by id to %s "


inline fun <reified T : View> Activity.widGet(@IdRes id: Int): T {
    val widGet by
    lazy(mode = LazyThreadSafetyMode.NONE) {
        requireNotNull(findViewById(id))
        {
            ON_FAILED_FIND_VIEW_BY_ID
                .format(T::class.java)
        }
    }
    return widGet as T
}

inline fun <reified T : View> View.widGet(@IdRes id: Int): T {
    val widGet by
    lazy(mode = LazyThreadSafetyMode.NONE) {
        requireNotNull(findViewById(id))
        {
            ON_FAILED_FIND_VIEW_BY_ID
                .format(T::class.java)
        }
    }
    return widGet as T
}