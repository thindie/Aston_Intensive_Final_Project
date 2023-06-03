package com.example.thindie.astonrickandmorty.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentLocationsBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.UiHolder
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UsesRecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class LocationsFragment : BaseFragment(), UsesRecyclerView {

    private lateinit var adapter: RecyclerViewAdapter<LocationsUiModel, UiHolder>

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LocationsViewModel by lazy { getVM(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsRouter.router.dispatchBackPress()
        searchEngine.observeSearchCriteria()
        viewModel.onClickedNavigationButton()
        setRecyclerView()
        observeRecyclerView()
    }

    override fun getHolderIdSupplier(): ViewHolderIdSupplier {
        return ViewHolderIdSupplier(
            viewHolderLayout = R.layout.item_grid_locations,
            majorChild = R.id.item_grid_locations_name,
            titleChild = R.id.item_grid_locations_type,
            lesserChild = R.id.item_grid_locations_dimension,
        )
    }

    override fun setRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerViewGridParent.recyclerViewGrid
        adapter = RecyclerViewAdapter(getHolderIdSupplier())
        recyclerView.adapter = adapter
    }

    override fun observeRecyclerView() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is OutsourceLogic.UiState.SuccessFetchResult<*> -> {
                    adapter.submitList(it.list.map { it.toUiEntity() })
                }
                else -> {}
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        return viewModel
    }

    override fun getSearchAbleList(): List<SearchAble> {
        return emptyList()
    }
}