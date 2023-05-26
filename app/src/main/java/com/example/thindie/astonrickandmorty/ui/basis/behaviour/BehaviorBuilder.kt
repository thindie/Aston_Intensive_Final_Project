package com.example.thindie.astonrickandmorty.ui.basis.behaviour

interface BehaviorBuilder {
    fun setChildProperty(property: Any): BehaviorBuilder
    fun switchToChildBehavior(): BehaviorBuilder
    fun setParentBehavior(): BehaviorBuilder
    fun setParentProperty(property: Any): BehaviorBuilder
    fun build(): Behaviour
    fun Behaviour.switch(): Behaviour
}
