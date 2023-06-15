package com.example.thindie.astonrickandmorty.ui.episodes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentEpisodesBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.FOC
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapterMediatorScroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.Scroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.ScrollListener
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UsesSearchAbleAdaptedRecycleViewAdapter
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchEngineResultConsumer


class EpisodesFragment : BaseFragment(), UsesSearchAbleAdaptedRecycleViewAdapter {

    private lateinit var _recyclerView: RecyclerView
    override val recyclerView: RecyclerView
        get() = _recyclerView

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!
    private val progress by lazy { binding.progressHorizontalEpisodes }

    private val viewModel: EpisodesViewModel by lazy { getVM(this) }
    private lateinit var listener: EventMediator<Scroll>

    private lateinit var childPropertyEpisodesList: List<String>

    private var isParent: Boolean = true
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            childPropertyEpisodesList =
                requireNotNull(arguments) {
                    FOC(getString(R.string.error_fragment_havent_proper_arguments))
                }.getStringArrayList(EPISODES)!!
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
                    viewModel
                        .adapter
                        .submitList(state.list.map { it.toUiEntity() })
                }
                is OutsourceLogic.UiState.BadResult -> {
                    FOC(state)
                }
                is OutsourceLogic.UiState.Loading -> {
                         state.show(progress)
                }
                else -> {

                }
            }
        }
    }

    override fun getHolderIdSupplier(): ViewHolderIdSupplier {
        if (isParent) {
            return ViewHolderIdSupplier(
                viewHolderLayout = R.layout.item_grid_personages,
                majorChild = R.id.item_grid_personages_name,
                titleChild = R.id.item_grid_personages_status,
                lesserChild = R.id.item_grid_personages_species,
                expandedChild = R.id.item_grid_personages_gender,
                imageChild = R.id.item_grid_personages_image
            )
        } else {
            return ViewHolderIdSupplier(
                viewHolderLayout = R.layout.item_list_personage_details_screen,
                majorChild = R.id.item_list_episodes_name,
                titleChild = R.id.item_list_episodes_episode,
                lesserChild = R.id.item_list_episodes_air_date
            )
        }
    }

    override fun setRecyclerView() {
        if (isParent) {
            _recyclerView =
                binding.recyclerViewGridParent.recyclerViewGrid
            binding.recyclerViewListParent.recyclerViewList.visibility = View.GONE

            viewModel.setAdapter(
                RecyclerViewAdapterMediatorScroll(viewHolderIdSupplier = getHolderIdSupplier(),
                    onClickedViewHolder = { fragmentsRouter.router.navigate(EpisodesConcreteFragment()) })
            )
            _recyclerView.adapter = viewModel.adapter
            if (_recyclerView.adapter is EventMediator<*>) {
                listener = _recyclerView.adapter as EventMediator<Scroll>
            }
        } else {

            _recyclerView =
                binding.recyclerViewListParent.recyclerViewList
            binding.recyclerViewGridParent.recyclerViewGrid.visibility = View.GONE

            viewModel.setAdapter(
                RecyclerViewAdapterMediatorScroll(viewHolderIdSupplier = getHolderIdSupplier(),
                    onClickedViewHolder = { FOC("CLIKED AT EPISODES") })
            )
            _recyclerView.adapter = viewModel.adapter
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


    override fun actAsAParentFragment() {
        fragmentsRouter.router.dispatchBackPress()
        searchEngine.observeSearchCriteria()
        setRecyclerView()
        observeRecyclerView()
        viewModel.onClickedNavigationButton()

    }

    override fun actAsAChildFragment() {
        setRecyclerView()
        observeRecyclerView()
        viewModel.onConcreteScreenObtainList(childPropertyEpisodesList)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        return viewModel
    }

    override fun getSearchAbleList(): List<SearchAble> {
        return viewModel.adapter.currentList
    }

    companion object {
        private const val IS_PARENT = "isParent"
        private const val EPISODES = "episodes"
        operator fun invoke(
            isParent: Boolean = true,
            episodesLinks: List<String>
        ): EpisodesFragment {
            return EpisodesFragment().apply {
                arguments = bundleOf(
                    IS_PARENT to isParent,
                    EPISODES to episodesLinks
                )
            }
        }
    }

}