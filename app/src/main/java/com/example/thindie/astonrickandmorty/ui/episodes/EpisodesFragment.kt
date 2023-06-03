package com.example.thindie.astonrickandmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentEpisodesBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.UiHolder
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UsesRecyclerView
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer


class EpisodesFragment : BaseFragment(), UsesRecyclerView {

    private lateinit var adapter: RecyclerViewAdapter<EpisodesUiModel, UiHolder>

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EpisodesViewModel by lazy { getVM(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsRouter.router.dispatchBackPress()
        searchEngine.observeSearchCriteria()
        setRecyclerView()
        observeRecyclerView()
        viewModel.onClickedNavigationButton()

    }

    override fun getHolderIdSupplier(): ViewHolderIdSupplier {
        return ViewHolderIdSupplier(
            viewHolderLayout = R.layout.item_grid_personages,
            majorChild = R.id.item_grid_personages_name,
            titleChild = R.id.item_grid_personages_status,
            lesserChild = R.id.item_grid_personages_species,
            expandedChild = R.id.item_grid_personages_gender,
            imageChild = R.id.item_grid_personages_image
        )
    }

    override fun setRecyclerView() {
        val recyclerView: RecyclerView = binding.recyclerViewGridParent.recyclerViewGrid
        adapter = RecyclerViewAdapter(getHolderIdSupplier())
        recyclerView.adapter = adapter
    }


    override fun observeRecyclerView() {
        viewModel.viewState.observe(viewLifecycleOwner) { UiState ->
            when (UiState) {
                is OutsourceLogic.UiState.SuccessFetchResult<*> -> {
                    adapter.submitList(UiState.list.map { it.toUiEntity() })
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
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
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
        return emptyList() //todo(
    }

}