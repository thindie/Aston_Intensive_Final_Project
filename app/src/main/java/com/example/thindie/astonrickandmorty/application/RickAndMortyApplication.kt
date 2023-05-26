package com.example.thindie.astonrickandmorty.application

import android.app.Application
import com.example.thindie.astonrickandmorty.Router

class RickAndMortyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(Router.Companion.ActivityLifeCycleGetter())
    }

}