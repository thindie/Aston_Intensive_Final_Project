package com.example.thindie.astonrickandmorty.ui.episodes

import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UiLinkPool
import com.example.thindie.astonrickandmorty.ui.uiutils.MinorComponent
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

data class EpisodesUiModel(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String,
    val pool: UiLinkPool
) : SearchAble {

    private var extra: MinorComponent? = null


    override fun <T : SearchAble> getReified(): T {
        return this  as T
    }

    override fun getMajorComponent(): String {
        return name
    }

    override fun getMajorComponent1(): String {
        return episode
    }

    override fun getMajorComponent2(): String {
        return airDate
    }

    override fun getExtraComponent(): List<String> {
        return characters
    }

    override fun getMinorComponent(): MinorComponent {
        return MinorComponent.SimpleStringHolder(EMPTY_VALUE)
    }

    override fun getMinorComponent1(): MinorComponent {
        return MinorComponent.SimpleStringHolder(EMPTY_VALUE)
    }

    override fun hasMinorComponents(): Boolean {
        return false
    }

    companion object {
        private const val EMPTY_VALUE = ""
    }
}