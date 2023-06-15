package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment

interface RecycleViewed {

    companion object {
        fun <Fragment : RecycleViewed> getInstance(list: List<String>): Fragment {
            return when (this::class) {
                PersonagesFragment::class -> {
                    PersonagesFragment.invoke(characterLinks = list) as Fragment
                }
                EpisodesFragment::class -> {
                    EpisodesFragment.invoke(episodesLinks = list) as Fragment
                }
                LocationsFragment::class -> {
                    LocationsFragment() as Fragment
                }
                else -> {
                    error("")
                }
            }
        }
    }
}
