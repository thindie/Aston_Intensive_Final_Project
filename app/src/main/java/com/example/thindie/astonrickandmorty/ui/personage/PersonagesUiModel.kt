package com.example.thindie.astonrickandmorty.ui.personage

import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UiLinkPool
import com.example.thindie.astonrickandmorty.ui.uiutils.MinorComponent
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

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
    val url: String,
    val pool: UiLinkPool

) : SearchAble {

    override fun <T : SearchAble> getReified(): T {
        return this as T
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

    override fun getExtraComponent(): List<String> {
        return episode
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