package com.example.thindie.astonrickandmorty.ui.personage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonagesBinding
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
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchEngineResultConsumer

class PersonagesFragment : BaseFragment(), UsesSearchAbleAdaptedRecycleViewAdapter, RecycleViewed {

    private var _binding: FragmentPersonagesBinding? = null
    private val binding get() = _binding!!
    private val progress by lazy { binding.progressHorizontalPersonages }
    private val viewModel: PersonagesViewModel by lazy { getVM(this) }

    private lateinit var listener: EventMediator<Scroll>

    private var isParent: Boolean = true
    private lateinit var childPropertyEpisodesList: List<String>

    private lateinit var _recyclerView: RecyclerView
    override val recyclerView: RecyclerView
        get() = _recyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            childPropertyEpisodesList =
                requireNotNull(arguments) {
                    FOC(getString(R.string.error_fragment_havent_proper_arguments))
                }.getStringArrayList( CHARACTERS)!!
            isParent = false
        } catch (e: Exception) { /* ignore */
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isParent) actAsAParentFragment() else actAsAChildFragment()

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is OutsourceLogic.UiState.SuccessFetchResult<*> -> {
                    state.hide(progress)
                    viewModel.adapter.submitList(state.list.map { it.toUiEntity() })
                }
                is OutsourceLogic.UiState.SuccessFetchResultConcrete<*> -> {
                    state.hide(progress)
                }
                is OutsourceLogic.UiState.BadResult -> {
                    FOC(state)
                }
                is OutsourceLogic.UiState.Loading -> {
                    state.show(progress)
                }
                else -> { /* ignore */
                }
            }

        }

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
        _recyclerView =
            binding.recyclerViewGridParent.recyclerViewGrid
        viewModel.setAdapter(
            RecyclerViewAdapterMediatorScroll(
                viewHolderIdSupplier = getHolderIdSupplier(),
                onClickedViewHolder = {
                    fragmentsRouter.router.navigate(PersonageConcreteFragment(it.id))
                })
        )
        _recyclerView.adapter = viewModel.adapter

        if (isParent) {
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


    override fun actAsAParentFragment() {

        fragmentsRouter.router.dispatchBackPress()
        viewModel.onClickedNavigationButton()
        searchEngine.observeSearchCriteria()
        setRecyclerView()
        observeRecyclerView()

    }

    override fun actAsAChildFragment() {
        setRecyclerView()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonagesBinding.inflate(inflater, container, false)
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

    companion object {
        private const val IS_PARENT = "isParent"
        private const val CHARACTERS = "characters"
        operator fun invoke(
            isParent: Boolean = true,
            characterLinks: List<String>
        ): PersonagesFragment {
            return PersonagesFragment().apply {
                arguments = bundleOf(
                    IS_PARENT to isParent,
                    CHARACTERS to characterLinks
                )
            }
        }
    }


}