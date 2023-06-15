package com.example.thindie.astonrickandmorty.ui.locations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentLocationsBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.FOC
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecycleViewed
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapterMediatorScroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.Scroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.ScrollListener
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UsesSearchAbleAdaptedRecycleViewAdapter
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchEngineResultConsumer

class LocationsFragment : BaseFragment(), UsesSearchAbleAdaptedRecycleViewAdapter, RecycleViewed {

    override val recyclerView: RecyclerView
        get() = _recyclerView
    private lateinit var _recyclerView: RecyclerView

    private lateinit var listener: EventMediator<Scroll>
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private val progress by lazy { binding.progressHorizontalLocations }

    private val viewModel: LocationsViewModel by lazy { getVM(this) }

    private var isParent: Boolean = true
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun actAsAParentFragment() {
        fragmentsRouter.router.dispatchBackPress()
        searchEngine.observeSearchCriteria()
        viewModel.onClickedNavigationButton()
        setRecyclerView()
        observeRecyclerView()
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is OutsourceLogic.UiState.SuccessFetchResult<*> -> {
                    viewModel.adapter.submitList(state.list.map { it.toUiEntity() })
                }
                is OutsourceLogic.UiState.BadResult -> { FOC(state) }
                is OutsourceLogic.UiState.Loading -> { state.show(progress) }
                else -> {
                 }
            }
        }
    }

    override fun actAsAChildFragment() {}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isParent) actAsAParentFragment() else actAsAChildFragment()
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
        if (isParent) {
            _recyclerView =
                binding.recyclerViewGridParent.recyclerViewGrid
            viewModel.setAdapter(RecyclerViewAdapterMediatorScroll(
                viewHolderIdSupplier = getHolderIdSupplier(),
                onClickedViewHolder = { fragmentsRouter.router.navigate(LocationsConcreteFragment()) }))
            _recyclerView.adapter = viewModel.adapter
            if (_recyclerView.adapter is EventMediator<*>) {
                listener = _recyclerView.adapter as EventMediator<Scroll>
            }
        }
    }

    override fun observeRecyclerView() {
        if (isParent) {
            viewModel.observe(listener)
            recyclerView.addOnScrollListener(
                ScrollListener(listener)
            )

        }
    }

    override fun onPause() {
        super.onPause()
        if (this::_recyclerView.isInitialized) recyclerView.clearOnScrollListeners()
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
        return viewModel.adapter.currentList
    }

}