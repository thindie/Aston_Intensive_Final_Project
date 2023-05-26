package com.example.thindie.astonrickandmorty.ui.searchBar


interface SearchBarControl {
    var producer: SearchBarEngine
    val consumer: SearchBarEngine
}

interface SearchBarEngine {
    fun search(list: List<SearchAble>, criteria: String? = null)
}

interface SearchAble {
    val searchingShadow: String
}


class SearchBarProducer private constructor(private val control: SearchBarControl) :
    SearchBarEngine {
    override fun search(list: List<SearchAble>, criteria: String?) {
        requireNotNull(criteria) { "Search Bar Producer have to use criteria" }
        val filteredList = list.filter { searchAble ->
            searchAble.searchingShadow.matchCriteria(criteria)
        }
        control.consumer.search(filteredList)
    }

    private fun String.matchCriteria(criteria: String): Boolean {
        return lowercase().contains(criteria.lowercase()) ||
                lowercase() == criteria.lowercase() ||
                criteria.lowercase().contains(this.lowercase())
    }

    companion object {
        fun inject(control: SearchBarControl) {
            val producer = SearchBarProducer(control)
            control.producer = producer
        }
    }
}
