package com.example.thindie.astonrickandmorty.application

import android.app.Application
import com.example.thindie.astonrickandmorty.application.di.AppComponent

class RickAndMortyApplication : Application() {

    val appComponent: AppComponent by lazy {
        AppComponent.install(applicationContext)
    }


}



