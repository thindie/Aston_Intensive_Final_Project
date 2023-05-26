package com.example.thindie.astonrickandmorty.ui.personage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonagesBinding
import com.example.thindie.astonrickandmorty.ui.MainViewModel
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.UsesRecyclerView
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.RecyclerViewAdapterMediatorScroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.Scroll
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.FOC
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.ScrollListener
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PersonagesFragment : BaseFragment(), UsesRecyclerView, SearchEngineUser {

    private var _binding: FragmentPersonagesBinding? = null
    private val binding get() = _binding!!
    private val progress by lazy { binding.progressHorizontalPersonages }

    private val personagesViewModel: PersonagesViewModel by viewModels { factory }

    private lateinit var listener: EventMediator<Scroll>
    private var isOpenedInsideFragment: Boolean = false

    private lateinit var _recyclerView: RecyclerView
    override val recyclerView: RecyclerView
        get() = _recyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isOpenedInsideFragment = try {
            requireNotNull(arguments).getBoolean(IS_OPENED_INSIDE)
        } catch (_: Exception) {
            false
        }

        if (isOpenedInsideFragment) {
            actAsAChildFragment()
        } else {
            actAsAParentFragment()
        }
    }

    private fun onSwipeRefresh() {
        binding.swiper.setOnRefreshListener {
            if (personagesViewModel.isRefreshNeeded()) {
                lifecycleScope.launch {
                    viewModel.onTriggerSectionEpisode()
                    delay(1000)
                    binding.swiper.isRefreshing = false
                }
            }
            viewModel.onEvent(
                this::class.java,
                personagesViewModel
                    .getPrevPool()
            )
            binding.swiper.isRefreshing = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        engine?.observeSearchCriteria()
        onSwipeRefresh()
        setRecyclerView()
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainViewModel.ScreenState.PersonagesScreenState -> {
                    val list = state
                        .holder
                        .screenUnit
                        .map { fetched ->
                            fetched.first
                        }.map {
                            it.getReified<PersonagesUiModel>()
                        }
                    personagesViewModel
                        .submitList(list)
                    state.hide(progress)
                }
                is MainViewModel.ScreenState.BadResult -> {
                    state.hide(progress)
                }
                is MainViewModel.ScreenState.Loading -> {
                    state.show(progress)
                }
                else -> {
                    state?.hide(progress)
                }
            }
        }
        personagesViewModel.emptySearchResult.observe(viewLifecycleOwner) {
            if (it) FOC(R.string.message_nothing_found)
        }
    }

    override fun setRecyclerView() {
        _recyclerView = binding.recyclerViewGridParent.recyclerViewGrid
        personagesViewModel.setAdapter(
            RecyclerViewAdapterMediatorScroll(
                viewHolderIdSupplier = ViewHolderIdSupplier.onObtainMasterSupplier(this::class.java),
                onClickedViewHolder = { complexModel ->
                    viewModel.onTriggerConcretePersonage(complexModel)
                    fragmentsRouter.navigate(PersonageConcreteFragment(), true)
                },
            )
        )
        _recyclerView.adapter = personagesViewModel.getAdapter()

        if (!isOpenedInsideFragment) {
            if (_recyclerView.adapter is EventMediator<*>) {
                listener = _recyclerView.adapter as EventMediator<Scroll>
                observeRecyclerView()
            }
        }
    }

    override fun observeRecyclerView() {
        observe(listener)
        recyclerView.addOnScrollListener(
            ScrollListener(listener)
        )
    }

    fun observe(listener: EventMediator<Scroll>) {
        listener.event = { scroll ->
            when (scroll) {
                Scroll.STILL -> {}
                Scroll.BOTTOM -> {
                    if (engine?.isSearchTriggered?.value == false) {
                        viewModel.onEvent(
                            this::class.java,
                            personagesViewModel
                                .getNextPool()
                        )
                    }
                }
                else -> {}
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (this::_recyclerView.isInitialized) recyclerView.clearOnScrollListeners()
    }

    override fun actAsAParentFragment() {
        viewModel.onTriggerSectionPersonage()
    }

    override fun actAsAChildFragment() = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val IS_OPENED_INSIDE = "isChild"
        fun getInstance(
            openAsChildInstance: Boolean,
        ): PersonagesFragment {
            return PersonagesFragment().apply {
                arguments = bundleOf(
                    IS_OPENED_INSIDE to openAsChildInstance,
                )
            }
        }
    }

    override fun getSearchingEngineManager(): SearchEngineManager {
        return searchEngine
    }

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        return personagesViewModel
    }

    override fun getSearchAbleList(): List<OperationAble> {
        return personagesViewModel.shareActualList()
    }
}
