package com.example.thindie.astonrickandmorty.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.application.FragmentsRouter
import com.example.thindie.astonrickandmorty.application.InjectController
import com.example.thindie.astonrickandmorty.application.Router
import com.example.thindie.astonrickandmorty.data.DataLayerDependencyProvider
import com.example.thindie.astonrickandmorty.domain.BaseRepository
import com.example.thindie.astonrickandmorty.domain.locations.LocationDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.ui.di.ActivityComponent
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchObservable
import javax.inject.Inject
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), FragmentsRouter, SearchEngineManager {
    private var searchObservable: SearchObservable? = null

    lateinit var dataLayerDependencyProvider: DataLayerDependencyProvider

    @Inject
    lateinit var repository: BaseRepository<LocationDomain>

    @Inject
    lateinit var personageRepository: BaseRepository<PersonageDomain>


    override val router: Router by lazy {
        Router.getInstance(this, R.id.activity_fragment_container, EpisodesFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        //тест даггер инит
        dataLayerDependencyProvider = (application as InjectController).provideInjector()
        ActivityComponent.install(dataLayerDependencyProvider).inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeBottomNavigation()
        router.navigate()



    }

    private fun observeBottomNavigation() {
        val navView: LinearLayout = findViewById(R.id.nav_bar)

        navView.findViewById<Button>(R.id.navigation_locations)
            .apply {
                setOnClickListener {
                    router.navigate(LocationsFragment())
                }
                text = "1"
            }



        navView.findViewById<Button>(R.id.navigation_episodes)
            .apply {
                setOnClickListener {
                    router.navigate(EpisodesFragment())
                }
                text = "2"
            }

        navView.findViewById<Button>(R.id.navigation_personages)
            .apply {
                setOnClickListener {
                    router.navigate(PersonagesFragment())
                }
                text = "3"
            }

    }

    override fun getSearchObserveAble(): SearchObservable {
        if (searchObservable == null) {
            searchObservable = SearchObservable.WidgetHolder(findViewById(R.id.search_bar))
        }
        return requireNotNull(searchObservable)
    }

}