package com.example.thindie.astonrickandmorty.ui.personage

import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.MinorComponent
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble

data class PersonagesUiModel(
    val id: Int,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val location: LocationUi,
    val name: String,
    val origin: OriginUi,
    val species: String,
    val status: String,
    val type: String,
    val url: String

) : SearchAble {

    override fun <T : SearchAble> getReified(): T {
        return this.toUiEntity<PersonagesUiModel>() as T
    }

    override fun getMajorComponent(): String {
        return name
    }

    override fun getMajorComponent1(): String {
        return species
    }

    override fun getMajorComponent2(): String {
        return status
    }

    override fun getMinorComponent(): MinorComponent {
        return MinorComponent.SimpleStringHolder(gender)
    }

    override fun getMinorComponent1(): MinorComponent {
        return MinorComponent.ImageUrlHolder(image)
    }

    override fun hasMinorComponents(): Boolean {
        return true
    }
}

data class LocationUi(
    val name: String,
    val url: String
)

data class OriginUi(
    val name: String,
    val url: String
)