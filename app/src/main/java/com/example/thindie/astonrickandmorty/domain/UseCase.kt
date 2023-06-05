package com.example.thindie.astonrickandmorty.domain

internal interface UseCase<T>{
    var useCase: CompositionUseCase<T>
}