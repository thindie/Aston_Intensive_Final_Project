package com.example.thindie.astonrickandmorty.ui.basis.operationables

interface WiderOperationAble : OperationAble {
    fun getWideComponent(): List<String>
    fun asOperationAble(): OperationAble
}
