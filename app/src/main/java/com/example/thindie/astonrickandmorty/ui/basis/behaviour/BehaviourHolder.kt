package com.example.thindie.astonrickandmorty.ui.basis.behaviour

interface BehaviourHolder {
    fun onObtainBehaviour(): Behaviour
    fun onStoreBehaviour(behaviour: Behaviour)
}
