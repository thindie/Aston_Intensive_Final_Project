package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment

interface RecycleViewed {

    companion object {
        fun <Fragment : RecycleViewed> getInstance(clazz: Class<Fragment>,list: List<String>): Fragment {
            return when (clazz) {
                PersonagesFragment::class.java -> {
                    PersonagesFragment.invoke(characterLinks = list, isParent = false) as Fragment
                }
                EpisodesFragment::class.java -> {
                    EpisodesFragment.invoke(episodesLinks = list, isParent = false) as Fragment
                }
                LocationsFragment::class.java -> {
                    LocationsFragment() as Fragment
                }
                else -> {
                    error("")
                }
            }
        }
    }
}
