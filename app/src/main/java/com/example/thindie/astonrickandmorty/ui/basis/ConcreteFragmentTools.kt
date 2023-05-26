package com.example.thindie.astonrickandmorty.ui.basis

import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble

interface ConcreteFragmentTools {
    fun initialiseParent(searchAble: OperationAble)
    fun initialiseChild(arguments: Any)
}
