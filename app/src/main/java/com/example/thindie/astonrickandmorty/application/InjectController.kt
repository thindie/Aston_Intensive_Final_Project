package com.example.thindie.astonrickandmorty.application

import com.example.thindie.astonrickandmorty.application.di.AppComponent

interface InjectController {
    fun provideInjector(): AppComponent
}