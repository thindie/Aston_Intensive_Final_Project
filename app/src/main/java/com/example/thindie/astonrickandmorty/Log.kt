package com.example.thindie.astonrickandmorty

import android.util.Log

fun Any.W(e: () -> String?) {
    Log.d("SERVICE_TAG", "${this::class.java.name} ---  ${e.invoke().toString()}")
}