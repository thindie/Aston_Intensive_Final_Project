package com.example.thindie.astonrickandmorty.ui.uiutils

import android.util.Log

inline fun <reified T : Any> T.qq(e: () -> String?) {
    Log.e("###", "${this::class.java.simpleName} --->>  ${e.invoke()}")
}
