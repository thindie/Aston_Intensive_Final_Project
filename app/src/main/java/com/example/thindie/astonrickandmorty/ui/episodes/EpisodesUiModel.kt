package com.example.thindie.astonrickandmorty.ui.episodes

import com.example.thindie.astonrickandmorty.ui.basis.operationables.MinorComponent
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.operationables.WiderOperationAble
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UiLinkPool

data class EpisodesUiModel(
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String,
    val pool: UiLinkPool
) : OperationAble, WiderOperationAble {
    override fun getWideComponent(): List<String> {
        return characters
    }

    override fun asOperationAble(): OperationAble {
        return this
    }

    override fun <T : OperationAble> getReified(): T {
        return this as T
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
