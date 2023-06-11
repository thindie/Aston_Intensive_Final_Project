package com.example.thindie.astonrickandmorty.ui.basis.recyclerview

import kotlinx.coroutines.flow.StateFlow

interface EventMediator<T> {
    val eventFlow: StateFlow<T>
    fun isActive(): Boolean
    fun setStatus(status: Int)
    fun onEvent()
    val statusActive: Int
        get() = ACTIVE
    val statusTurnedOff
        get() = TURNED_OFF
}

private const val ACTIVE = 1
private const val TURNED_OFF = 0