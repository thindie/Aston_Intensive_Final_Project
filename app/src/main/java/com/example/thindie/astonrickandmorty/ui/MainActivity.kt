package com.example.thindie.astonrickandmorty.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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
import com.example.thindie.astonrickandmorty.ui.uiutils.widGet
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentsRouter, SearchEngineManager {
    private var searchObservable: SearchObservable? = null




/*lateinit var dataLayerDependencyProvider: DataLayerDependencyProvider
    @Inject
    lateinit var repository: BaseRepository<LocationDomain>

    @Inject
    lateinit var personageRepository: BaseRepository<PersonageDomain>*/


    override val router: Router by lazy {
        Router.getInstance(this, R.id.activity_fragment_container, PersonagesFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {

      /*  dataLayerDependencyProvider = (application as InjectController).provideInjector()
        ActivityComponent.install(dataLayerDependencyProvider).inject(this)*/

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeBottomNavigation()
        router.navigate()

    }

    private fun observeBottomNavigation() {

        widGet<Button>(R.id.navigation_locations)
            .setOnClickListener { router.navigate(LocationsFragment()) }

        widGet<Button>(R.id.navigation_episodes)
            .setOnClickListener { router.navigate(EpisodesFragment()) }

        widGet<Button>(R.id.navigation_personages)
            .setOnClickListener { router.navigate(PersonagesFragment()) }

    }

    override fun getSearchObserveAble(): SearchObservable {
        if (searchObservable == null) {
            searchObservable = SearchObservable.WidgetHolder(widGet(R.id.search_bar))
        }
        return requireNotNull(searchObservable)
    }
}