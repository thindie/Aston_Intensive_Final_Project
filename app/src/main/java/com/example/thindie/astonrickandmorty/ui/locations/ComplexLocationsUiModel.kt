package com.example.thindie.astonrickandmorty.ui.locations

import com.example.thindie.astonrickandmorty.ui.basis.operationables.ComplexOperationAble
import com.example.thindie.astonrickandmorty.ui.basis.operationables.MinorComponent
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesUiModel

data class ComplexLocationsUiModel(
    val uiModel: LocationsUiModel,
    val list: List<PersonagesUiModel>
) :
    ComplexOperationAble {
    override fun isComplexComponentNeeded(): Boolean {
        return true
    }

    override fun getComplexComponent(): List<OperationAble> {
        return list
    }

    override fun <T : OperationAble> getReified(): T {
        return this as T
    }

    override fun getMajorComponent(): String {
        return uiModel.getMajorComponent()
    }

    override fun getMajorComponent1(): String {
        return uiModel.getMajorComponent1()
    }

    override fun getMajorComponent2(): String {
        return uiModel.getMajorComponent2()
    }

    override fun getMinorComponent(): MinorComponent {
        return uiModel.getMinorComponent()
    }

    override fun getMinorComponent1(): MinorComponent {
        return uiModel.getMinorComponent1()
    }

    override fun hasMinorComponents(): Boolean {
        return uiModel.hasMinorComponents()
    }
}
