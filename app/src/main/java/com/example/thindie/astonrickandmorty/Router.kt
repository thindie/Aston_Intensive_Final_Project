package com.example.thindie.astonrickandmorty

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 *  Implement [FragmentsRouter] by actual activity,
 *  make
 *  registration
 *  [Router.Companion.ActivityLifeCycleGetter]
 *  in application's class 'onCreate()'
 *
 *
 *  in Activity:
 *
 *  override val router: Router by lazy {
 *         Router.inject(
 *             contactRouter = this@AppCompatActivity,
 *             id = R.id.<activity_fragment_container>,
 *             startDestination = 'Fragment' ) }
 *
 *  in Application:
 *
 *  override fun onCreate() {
 *     super.onCreate()
 *     registerActivityLifecycleCallbacks(Router.Companion.ActivityLifeCycleGetter())
 *  }
 *
 *
 *
 *
 */

interface FragmentsRouter {
    val router: Router
}

class Router private constructor(
    appCompatActivity: AppCompatActivity?,
    @IdRes private val container: Int,
    private val startScreen: Fragment,
) {
    private val appCompatActivity = requireNotNull(appCompatActivity) { "Activity not init " }
    private val startDestination: String = startScreen::class.java.name

    fun navigate(fragment: Fragment? = null) {
        if (fragment == null) onInit()
        else {
            val isNotExist = appCompatActivity
                .supportFragmentManager
                .findFragmentByTag(fragment::class.java.name) == null

            if (isNotExist) navigateFragmentAtFirstTime(fragment)
            else navigateExisting(fragment)
        }
    }

    private fun onInit() {
        if (currentDestination == null) navigateStartDestination()
        else restoreExistingAtConfigurationChange()
    }

    private fun navigateStartDestination() {
        appCompatActivity
            .supportFragmentManager.beginTransaction()
            .replace(
                container,
                startScreen,
                currentDestination
            )
            .commit()
    }

    private fun navigateExisting(fragment: Fragment) {
        currentDestination = fragment::class.java.name
        appCompatActivity.supportFragmentManager
            .beginTransaction()
            .replace(
                container,
                appCompatActivity
                    .supportFragmentManager
                    .findFragmentByTag(currentDestination)
                    ?: startScreen
            ).commit()
    }

    private fun restoreExistingAtConfigurationChange() {
        appCompatActivity.supportFragmentManager
            .beginTransaction()
            .replace(
                container,
                appCompatActivity
                    .supportFragmentManager
                    .findFragmentByTag(currentDestination)
                    ?: startScreen
            ).commit()
    }

    private fun navigateFragmentAtFirstTime(fragment: Fragment) {
        currentDestination = fragment::class.java.name
        appCompatActivity
            .supportFragmentManager.beginTransaction()
            .replace(
                container,
                fragment,
                currentDestination
            )
            .also { if (currentDestination != startDestination) it.addToBackStack(currentDestination) }
            .commit()
    }

    companion object {
        private var currentDestination: String? = null
        private val DESTINATION = Router::class.java.simpleName

        class ActivityLifeCycleGetter : ActivityLifecycleCallbacks {
            private val tag = this::class.java.simpleName
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                Log.i(tag, "${CallbackState.CREATED} ${p1?.getString(DESTINATION).toString()}")
                currentDestination = p1?.getString(DESTINATION)
            }

            override fun onActivityStarted(p0: Activity) {
                Log.i(tag, "$tag ${CallbackState.STARTED}")
            }

            override fun onActivityResumed(p0: Activity) {
                Log.i(tag, "$tag ${CallbackState.RESUMED}")
            }

            override fun onActivityPaused(p0: Activity) {
                Log.i(tag, "$tag ${CallbackState.PAUSED}")
            }

            override fun onActivityStopped(p0: Activity) {
                Log.i(tag, "$tag ${CallbackState.STOPPED}")
                currentDestination
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                p1.putString(DESTINATION, currentDestination)
            }

            override fun onActivityDestroyed(p0: Activity) {
                Log.i(tag, "$tag ${CallbackState.DESTROYED}")
            }

            enum class CallbackState {
                STARTED, RESUMED, PAUSED, STOPPED, DESTROYED, CREATED
            }

        }

        fun inject(
            contactRouter: FragmentsRouter,
            id: Int,
            startDestination: Fragment,
        ): Router {
            return Router(
                contactRouter as? AppCompatActivity,
                id,
                startDestination
            )
        }
    }
}