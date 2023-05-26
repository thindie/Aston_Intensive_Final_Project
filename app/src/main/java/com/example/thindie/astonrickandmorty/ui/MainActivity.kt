package com.example.thindie.astonrickandmorty.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.commit
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.application.RickAndMortyApplication
import com.example.thindie.astonrickandmorty.application.di.AppComponent
import com.example.thindie.astonrickandmorty.ui.basis.BaddestResultFragment
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.FOC
import com.example.thindie.astonrickandmorty.ui.basis.ViewModelFactory
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier.Companion.onObtainCommonBottomSheet
import com.example.thindie.astonrickandmorty.ui.basis.rootsList
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.BottomSheetFiller
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.bottomsheet.core.CommonBottomSheet
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchObserveAble
import com.example.thindie.astonrickandmorty.ui.uiutils.widGet
import javax.inject.Inject

class MainActivity : AppCompatActivity(), SearchEngineManager, FragmentsRouter {

    @Inject
    lateinit var factory: ViewModelFactory

    val viewModel: MainViewModel by viewModels { factory }

    private val injector: AppComponent by lazy {
        (application as RickAndMortyApplication).appComponent
    }

    private val observeAble by lazy { getSearchObserveAble() }
    private val clickAbles by lazy { observeClickableElements() }

    private val arrow: ImageView by lazy {
        widGet(R.id.activity_back_press_image_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injector.inject(this)
        clickAbles
        dispatchBackPress()
        observeBackArrow()
        observeRootUiElementState()
        val restoredScreen = viewModel.restoreLastSavedScreen()
        activateCriticalEventDispatch()
        if (restoredScreen == null) {
            navigate(null, false)
        } else {
            restore(restoredScreen, false)
        }
    }

    private fun activateCriticalEventDispatch() {
        viewModel.screenState.observe(this) { state ->
            when (state) {
                is MainViewModel.ScreenState.BadResult -> {
                    FOC(state.e)
                }
                is MainViewModel.ScreenState.SomeVeryBadSituation -> {
                    navigate(BaddestResultFragment(), false)
                }
                else -> { /* ignore */
                }
            }
        }
    }

    private fun observeBackArrow() {
        arrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeClickableElements(): List<View> {
        val clickAbles: MutableList<View> = mutableListOf()
        val locations =
            widGet<ImageButton>(com.example.thindie.astonrickandmorty.R.id.navigation_locations)
        locations
            .setOnClickListener {
                viewModel.showArrowNot()
                viewModel.rememberLastRootScreen(LocationsFragment()::class.java)
                navigate(LocationsFragment(), false)
            }

        val episodes =
            widGet<ImageButton>(R.id.navigation_episodes)
        episodes
            .setOnClickListener {
                viewModel.showArrowNot()
                viewModel.rememberLastRootScreen(EpisodesFragment()::class.java)
                navigate(EpisodesFragment(), false)
            }

        val navigation =
            widGet<ImageButton>(R.id.navigation_personages)
        navigation
            .setOnClickListener {
                viewModel.showArrowNot()
                viewModel.rememberLastRootScreen(PersonagesFragment()::class.java)
                navigate(PersonagesFragment(), false)
            }

        val filtering =
            widGet<ImageView>(R.id.activity_filter_image_view)
        filtering
            .setOnClickListener {
                ActivityBottomSheet().show(supportFragmentManager, null)
            }
        clickAbles += locations
        clickAbles += episodes
        clickAbles += navigation
        clickAbles += filtering
        return clickAbles
    }

    override fun getSearchObserveAble(): SearchObserveAble {
        return SearchObserveAble.WidgetHolder(widGet(R.id.search_bar_layout))
    }

    private fun adjustSearchObserveAbleToScreen(
        searchObserveAble: SearchObserveAble,
        fragment: Class<out Fragment>?,
    ) {
        when (searchObserveAble) {
            is SearchObserveAble.WidgetHolder -> {
                (observeAble as SearchObserveAble.WidgetHolder)
                    .setClear()
                    .visibility(isDestinationRoot(fragment))
            }
            else -> {
            }
        }
    }

    private fun observeRootUiElementState() {
        viewModel
            .rootUiState
            .observe(this) { root ->
                if (root.isArrowStateShouldShow) {
                    root.show(arrow)
                } else {
                    root.hide(arrow)
                }
                if (root.isElementsActive) {
                    clickAbles
                        .forEach { clickAbles -> root.availAble(clickAbles) }
                    root.availAble(arrow)
                } else {
                    clickAbles
                        .forEach { clickAbles -> root.locked(clickAbles) }
                    root.locked(arrow)
                }
            }
    }

    override fun navigate(fragment: Fragment?, isAddToBackStack: Boolean) {
        val navigationUnit = fragment ?: PersonagesFragment()

        adjustSearchObserveAbleToScreen(observeAble, navigationUnit::class.java)
        supportFragmentManager.commit {
            replace(
                R.id.activity_fragment_container,
                navigationUnit,
                navigationUnit::class.java.name
            )
            setReorderingAllowed(true)
            if (isAddToBackStack) addToBackStack(navigationUnit::class.java.name)
            setTransition(TRANSIT_FRAGMENT_FADE)
        }

        viewModel.rememberCurrentScreen(navigationUnit::class.java)
        if (rootsList.contains(navigationUnit::class.java)) {
            viewModel.rememberLastRootScreen(
                navigationUnit::class.java
            )
        }
    }

    override fun restore(fragment: Class<out Fragment>?, isAddToBackStack: Boolean) {
        val navigationUnit =
            supportFragmentManager.findFragmentByTag(fragment?.name) ?: PersonagesFragment()

        adjustSearchObserveAbleToScreen(observeAble, fragment)
        supportFragmentManager.commit {
            replace(
                R.id.activity_fragment_container,
                navigationUnit,
                navigationUnit::class.java.name
            )
            setReorderingAllowed(true)
            if (isAddToBackStack) addToBackStack(navigationUnit::class.java.name)
            setTransition(TRANSIT_FRAGMENT_FADE)
        }

        viewModel.rememberCurrentScreen(fragment)
    }

    private fun isDestinationRoot(fragment: Class<out Fragment>?): Boolean {
        if (fragment == null) return true
        return rootsList.contains(fragment)
    }

    private fun dispatchBackPress() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val lastSavedClass = viewModel.restoreLastSavedScreen()
                    if (!isDestinationRoot(lastSavedClass) && lastSavedClass != null) {
                        viewModel.rememberCurrentScreen(viewModel.getLastVisitedRootFragment())
                    }
                    if (isDestinationRoot(lastSavedClass)) {
                        viewModel.rememberCurrentScreen(null)
                    }
                    if (viewModel.restoreLastSavedScreen() == null) {
                        finish()
                    } else {
                        restore(lastSavedClass, true)
                    }
                }
            }
        )
    }

    class ActivityBottomSheet : CommonBottomSheet<BottomSheetFiller>(
        supplier = onObtainCommonBottomSheet(ActivityBottomSheet::class.java)
    )
}
