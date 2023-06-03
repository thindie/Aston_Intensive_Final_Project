package com.example.thindie.astonrickandmorty.application

import android.app.Application
import com.example.thindie.astonrickandmorty.application.di.AppComponent

class RickAndMortyApplication : Application(), InjectController {

    private var appComponent: AppComponent? = null
    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(Router.Companion.ActivityLifeCycleGetter())
    }

    override fun provideInjector(): AppComponent {
        return injector()
    }

    private fun injector(): AppComponent {
        if (appComponent == null) {
            appComponent = AppComponent.install(applicationContext)
        }
        return requireNotNull(appComponent) { "AppComponent on Application not init" }
    }

}



