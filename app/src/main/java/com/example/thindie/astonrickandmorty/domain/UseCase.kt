package com.example.thindie.astonrickandmorty.domain

interface UseCase<T>{
    var useCase: CompositionUseCase<T>
}