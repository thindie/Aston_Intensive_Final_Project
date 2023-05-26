package com.example.thindie.astonrickandmorty.ui.basis

import androidx.fragment.app.Fragment
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesConcreteFragment
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsConcreteFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonageConcreteFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment

val rootsList: List<Class<out Fragment>> = listOf(
    EpisodesFragment::class.java,
    LocationsFragment::class.java,
    PersonagesFragment::class.java
)

val primaryNavigators: List<Class<out Fragment>> = listOf(
    EpisodesConcreteFragment::class.java,
    LocationsConcreteFragment::class.java,
    PersonageConcreteFragment::class.java
)
