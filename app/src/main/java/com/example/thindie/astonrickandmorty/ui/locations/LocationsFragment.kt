package com.example.thindie.astonrickandmorty.ui.locations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.databinding.FragmentLocationsBinding
import com.example.thindie.astonrickandmorty.ui.MainViewModel
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.UsesRecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable.ComplexedRecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.Scroll
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.FOC
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.ScrollListener
import com.example.thindie.astonrickandmorty.ui.personage.PersonageConcreteFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngine
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LocationsFragment : BaseFragment(), UsesRecyclerView, SearchEngineUser {

    override val recyclerView: RecyclerView
        get() = _recyclerView
    private lateinit var _recyclerView: RecyclerView

    private lateinit var listener: EventMediator<Scroll>
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private val progress by lazy { binding.progressHorizontalLocations }

    private val locationsViewModel: LocationsViewModel by viewModels { factory }

    private var isParent: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SearchEngine.inject(this, lifecycleScope)
        viewModel.onTriggerSectionLocation()
    }

    override fun actAsAParentFragment() = Unit

    private fun onSwipeRefresh() {
        binding.swiper.setOnRefreshListener {
            if (locationsViewModel.isRefreshNeeded()) {
                lifecycleScope.launch {
                    viewModel.onTriggerSectionEpisode()
                    delay(1000)
                    binding.swiper.isRefreshing = false
                }
            }
            viewModel.onEvent(
                this::class.java,
                locationsViewModel
                    .getPrevPool()
            )
            binding.swiper.isRefreshing = false
        }
    }

    override fun actAsAChildFragment() {}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        engine?.observeSearchCriteria()
        onSwipeRefresh()
        if (isParent) actAsAParentFragment() else actAsAChildFragment()
        setRecyclerView()
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainViewModel.ScreenState.LocationsScreenState -> {
                    val list = state
                        .holder
                        .screenUnit
                        .map { fetched ->
                            ComplexLocationsUiModel(
                                fetched.first.getReified(),
                                list = fetched.second.map { it.getReified() }
                            )
                        }
                    locationsViewModel
                        .submitList(list)

                    state.hide(progress)
                }
                is MainViewModel.ScreenState.BadResult -> {
                    state.hide(progress)
                }
                is MainViewModel.ScreenState.Loading -> {
                    state.show(progress)
                }
                else -> { /*ignore*/
                }
            }
        }
        locationsViewModel.emptySearchResult.observe(viewLifecycleOwner) {
            if (it) FOC("Nothing found here, sorry")
        }
    }

    override fun setRecyclerView() {
        _recyclerView =
            binding.recyclerViewGridParentLocations

        locationsViewModel.setAdapter(
            ComplexedRecyclerViewAdapter(
                viewHolderIdSupplier = ViewHolderIdSupplier.onObtainMasterSupplier(this::class.java),
                slaveViewHolderIdSupplier = ViewHolderIdSupplier.onObtainChildSupplier(this::class.java),
                onClickedSlaveViewHolder = {
                    viewModel.onTriggerConcretePersonage(it.getReified())
                    fragmentsRouter.navigate(PersonageConcreteFragment(), true)
                },
                onClickedViewHolder = {
                    viewModel.onTriggerConcreteLocation(it.uiModel.getReified())
                    fragmentsRouter.navigate(LocationsConcreteFragment(), true)
                },
            )

        )
        _recyclerView.adapter = locationsViewModel.getAdapter()

        if (_recyclerView.adapter is EventMediator<*>) {
            listener = _recyclerView.adapter as EventMediator<Scroll>
        }
    }

    override fun observeRecyclerView() {
        if (isParent) {
            observe(listener)
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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observe(eventStateListener: EventMediator<Scroll>) {
        eventStateListener.event = { scroll ->
            when (scroll) {
                Scroll.STILL -> { /* ignore */
                }
                Scroll.BOTTOM -> {
                    if (!engine?.isSearchTriggered?.value!!) {
                        viewModel.onEvent(
                            this::class.java,
                            locationsViewModel
                                .getNextPool()
                        )
                    }
                }

                Scroll.TOP -> { /* ignore */
                }
            }
        }
    }

    override fun getSearchingEngineManager(): SearchEngineManager {
        return searchEngine
    }

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        return locationsViewModel
    }

    override fun getSearchAbleList(): List<OperationAble> {
        return getSearchingConsumer().shareActualList()
    }
}
