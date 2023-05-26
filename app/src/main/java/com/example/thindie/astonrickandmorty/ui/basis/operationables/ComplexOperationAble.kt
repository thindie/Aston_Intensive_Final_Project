package com.example.thindie.astonrickandmorty.ui.basis.operationables

interface ComplexOperationAble : OperationAble {
    fun isComplexComponentNeeded(): Boolean
    fun getComplexComponent(): List<OperationAble>
}
