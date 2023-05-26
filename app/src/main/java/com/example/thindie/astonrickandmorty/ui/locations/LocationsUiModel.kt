package com.example.thindie.astonrickandmorty.ui.locations

import com.example.thindie.astonrickandmorty.ui.basis.operationables.MinorComponent
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.operationables.WiderOperationAble
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UiLinkPool

data class LocationsUiModel(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String,
    val pool: UiLinkPool
) : OperationAble, WiderOperationAble {
    override fun getWideComponent(): List<String> {
        return residents
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
