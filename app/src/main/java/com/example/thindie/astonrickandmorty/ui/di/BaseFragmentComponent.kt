package com.example.thindie.astonrickandmorty.ui.di

import com.example.thindie.astonrickandmorty.data.DataLayerDependencyProvider
import com.example.thindie.astonrickandmorty.domain.di.DomainComponent
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import dagger.Component

@Component(
    dependencies = [DataLayerDependencyProvider::class, DomainComponent::class, ViewModelsComponent::class],

)
interface BaseFragmentComponent {
    companion object {
        fun install(component: DataLayerDependencyProvider): BaseFragmentComponent {
            val domainComponent: DomainComponent = DomainComponent.install(component)
            val viewModelsComponent = ViewModelsComponent.install(component,domainComponent)
            return DaggerFragmentComponent.factory.create(component, domainComponent, viewModelsComponent)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            component: DataLayerDependencyProvider,
            domainComponent: DomainComponent,
            viewModelsComponent: ViewModelsComponent
        ): BaseFragmentComponent
    }

    fun inject(baseFragment: BaseFragment)
}