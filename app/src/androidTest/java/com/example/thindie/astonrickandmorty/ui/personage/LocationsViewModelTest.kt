package com.example.thindie.astonrickandmorty.ui.personage

import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable.ComplexUiHolder
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable.ComplexedRecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.locations.ComplexLocationsUiModel
import com.example.thindie.astonrickandmorty.ui.locations.LocationsUiModel
import com.example.thindie.astonrickandmorty.ui.locations.LocationsViewModel
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk

class LocationsViewModelTest :
    BehaviorSpec({
        var viewModel: LocationsViewModel
        val adapter: ComplexedRecyclerViewAdapter<ComplexLocationsUiModel, ComplexUiHolder> =
            mockk()

        val locationsUiModel = mockk<LocationsUiModel>()
        val list: List<PersonagesUiModel> = mockk()

        Given("a LocationsViewModel") {
            viewModel = LocationsViewModel()
            viewModel.setAdapter(adapter)
            When("getNextPool method is called") {
                val pool = viewModel.getNextPool()

                Then("pool should be an empty string") {
                    assert(pool.isBlank())
                }
                And("getPrevPool method is called") {
                    val pool = viewModel.getPrevPool()

                    Then("pool should be an empty string") {
                        assert(pool.isBlank())
                    }
                }

                And("isRefreshNeeded method is called") {
                    val isRefreshNeeded = viewModel.isRefreshNeeded()

                    Then("isRefreshNeeded should be true") {
                        assert(isRefreshNeeded)
                    }
                }
            }
        }
    })
