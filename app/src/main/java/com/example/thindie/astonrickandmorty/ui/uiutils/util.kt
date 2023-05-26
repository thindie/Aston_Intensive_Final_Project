@file:Suppress("Unchecked_cast")

package com.example.thindie.astonrickandmorty.ui.uiutils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import com.example.thindie.astonrickandmorty.R

const val ON_FAILED_FIND_VIEW_BY_ID = " nothing found by id to %s "

inline fun <reified T : View> Activity.widGet(@IdRes id: Int): T {
    val widGet by
    lazy(mode = LazyThreadSafetyMode.NONE) {
        requireNotNull(findViewById(id)) {
            ON_FAILED_FIND_VIEW_BY_ID
                .format(T::class.java)
        }
    }
    return widGet as T
}

inline fun <reified T : View> View.widGet(@IdRes id: Int): T {
    val widGet by
    lazy(mode = LazyThreadSafetyMode.NONE) {
        requireNotNull(findViewById(id)) {
            ON_FAILED_FIND_VIEW_BY_ID
                .format(T::class.java)
        }
    }
    return widGet as T
}

fun TextView.organise(string: String, url: String, onClick: (String) -> Unit) {
    text = string
    if (string.isBlank() ||
        string.lowercase() == "unknown".lowercase() ||
        string.lowercase() == "empty".lowercase()
    ) {
        return
    }
    setOnClickListener {
        onClick(url)
    }
}

fun ImageView.colorReady() {
    setColorFilter(R.color.black)
}

fun ImageView.colorAwait() {
    setColorFilter(Color.GRAY)
}

fun rotate(imageView: ImageView) {
    val rotate = RotateAnimation(
        0f,
        360f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        duration = 1900
        repeatCount = Animation.INFINITE
    }
    imageView.startAnimation(rotate)
}

fun beatMotion(imageView: ImageView) {
    val rotate = TranslateAnimation(
        0f ,
        34f,
        0f,
        34f
    ).apply {
        duration = 400
        repeatCount =4
    }
    imageView.startAnimation(rotate)
}
