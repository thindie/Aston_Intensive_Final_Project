package com.example.thindie.astonrickandmorty.ui.episodes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.databinding.FragmentEpisodesBinding
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
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngine
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EpisodesFragment : BaseFragment(), UsesRecyclerView, SearchEngineUser {

    private lateinit var _recyclerView: RecyclerView
    override val recyclerView: RecyclerView
        get() = _recyclerView

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!
    private val progress by lazy { binding.progressHorizontalEpisodes }

    private lateinit var listener: EventMediator<Scroll>
    private val episodesViewModel: EpisodesViewModel by viewModels { factory }

    private var isUsedByConcreteParent: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SearchEngine.inject(this, lifecycleScope)
        isUsedByConcreteParent = arguments?.getBoolean(IS_USED_BY_AS_CHILD) == true
    }

    private fun onSwipeRefresh() {
        binding.swiper.setOnRefreshListener {
            if (episodesViewModel.isRefreshNeeded()) {
                lifecycleScope.launch {
                    viewModel.onTriggerSectionEpisode()
                    delay(1000)
                    binding.swiper.isRefreshing = false
                }
            }
            viewModel.onEvent(
                this::class.java,
                episodesViewModel
                    .getPrevPool()
            )
            binding.swiper.isRefreshing = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        engine?.observeSearchCriteria()
        setRecyclerView()
        if (isUsedByConcreteParent) actAsAChildFragment() else actAsAParentFragment()
        onSwipeRefresh()
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainViewModel.ScreenState.EpisodesScreenState -> {
                    val list = state
                        .holder
                        .apply { if (screenUnit.isEmpty()) viewModel.onPopulateThatResultIsEmpty() }
                        .screenUnit
                        .map { fetched ->
                            ComplexEpisodesUiModel(
                                fetched.first.getReified(),
                                list = fetched
                                    .second
                                    .map { it.getReified() }
                            )
                        }
                    episodesViewModel
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
        episodesViewModel.emptySearchResult.observe(viewLifecycleOwner) {
            if (it) FOC("Nothing found here, sorry")
        }
    }

    override fun setRecyclerView() {
        _recyclerView =
            binding.recyclerViewGridParentEpisodes

        recyclerView.layoutManager = if (isUsedByConcreteParent) {
            LinearLayoutManager(
                /* context = */
                requireActivity(),
                /* orientation = */
                LinearLayoutManager.VERTICAL,
                /* reverseLayout = */
                false
            )
        } else {
            GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        }

        episodesViewModel.setAdapter(
            ComplexedRecyclerViewAdapter(
                viewHolderIdSupplier = ViewHolderIdSupplier.onObtainMasterSupplier(this::class.java),
                slaveViewHolderIdSupplier = ViewHolderIdSupplier.onObtainChildSupplier(this::class.java),
                onClickedSlaveViewHolder = {
                    viewModel.onTriggerConcretePersonage(it.getReified())
                    fragmentsRouter.navigate(PersonageConcreteFragment(), true)
                },
                onClickedViewHolder = { complexModel ->
                    viewModel.onTriggerConcreteEpisode(complexModel.uiModel)
                    fragmentsRouter.navigate(EpisodesConcreteFragment(), true)
                },
            )
        )
        _recyclerView.adapter = episodesViewModel.getAdapter()
    }

    override fun observeRecyclerView() {
        if (_recyclerView.adapter is EventMediator<*>) {
            listener = _recyclerView.adapter as EventMediator<Scroll>

            observe(listener)
            recyclerView.addOnScrollListener(
                ScrollListener(listener)
            )
        }
    }

    override fun actAsAParentFragment() {
        viewModel.onTriggerSectionEpisode()
        observeRecyclerView()
    }

    override fun actAsAChildFragment() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    fragmentsRouter.navigate(PersonagesFragment(), true)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        if (this::_recyclerView.isInitialized) recyclerView.clearOnScrollListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observe(eventStateListener: EventMediator<Scroll>) {
        eventStateListener.event = { scroll ->
            when (scroll) {
                Scroll.STILL -> { /* ignore */ }
                Scroll.BOTTOM -> {
                    if (!engine?.isSearchTriggered?.value!!) {
                        viewModel.onEvent(
                            this::class.java,
                            episodesViewModel
                                .getNextPool()
                        )
                    }
                }
                Scroll.TOP -> { /* ignore */ }
            }
        }
    }

    companion object {
        private const val IS_USED_BY_AS_CHILD = "child"

        fun getInstance(isChild: Boolean): EpisodesFragment {
            return EpisodesFragment().apply {
                arguments =
                    bundleOf(IS_USED_BY_AS_CHILD to isChild)
            }
        }
    }

    override fun getSearchingEngineManager(): SearchEngineManager {
        return searchEngine
    }

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        return episodesViewModel
    }

    override fun getSearchAbleList(): List<OperationAble> {
        return getSearchingConsumer().shareActualList()
    }
}
