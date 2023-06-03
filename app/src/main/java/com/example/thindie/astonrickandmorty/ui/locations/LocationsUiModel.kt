package com.example.thindie.astonrickandmorty.ui.locations

import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.MinorComponent
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble

data class LocationsUiModel(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
) : SearchAble {


    override fun <T : SearchAble> getReified(): T {
        return this.toUiEntity<LocationsUiModel>() as T
    }

    override fun getMajorComponent(): String {
        return name
    }

    override fun getMajorComponent1(): String {
        return type
    }

    override fun getMajorComponent2(): String {
        return dimension
    }

    override fun getMinorComponent(): MinorComponent {
        return MinorComponent.SimpleStringHolder(EMPTY_VALUE)
    }

    override fun getMinorComponent1(): MinorComponent {
        return getMinorComponent()
    }

    override fun hasMinorComponents(): Boolean {
        return false
    }

    companion object {
        private const val EMPTY_VALUE = ""
    }
}
