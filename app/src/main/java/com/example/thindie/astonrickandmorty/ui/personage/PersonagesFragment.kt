package com.example.thindie.astonrickandmorty.ui.personage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonagesBinding
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

class PersonagesFragment : BaseFragment(), UsesSearchAbleAdaptedRecycleViewAdapter {

    private var _binding: FragmentPersonagesBinding? = null

    private val binding get() = _binding!!
    private val viewModel: PersonagesViewModel by lazy { getVM(this) }

    private lateinit var listener: EventMediator<Scroll>

    private var isParent: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            isParent = requireArguments().getBoolean(IS_PARENT)
        } catch (e : Exception){ /* ignore */ }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(isParent) actAsAParentFragment()
        else actAsAChildFragment()

    }

    private lateinit var _recyclerView: RecyclerView
    override val recyclerView: RecyclerView
        get() = _recyclerView

    override fun getHolderIdSupplier(): ViewHolderIdSupplier {
        if(isParent) {
            return ViewHolderIdSupplier(
                viewHolderLayout = R.layout.item_grid_personages,
                majorChild = R.id.item_grid_personages_name,
                titleChild = R.id.item_grid_personages_status,
                lesserChild = R.id.item_grid_personages_species,
                expandedChild = R.id.item_grid_personages_gender,
                imageChild = R.id.item_grid_personages_image
            )
        }
        else {
            return ViewHolderIdSupplier(
                viewHolderLayout = 0,
                majorChild = 0,
                titleChild = 0,
                lesserChild = 0,
                expandedChild = null,
                imageChild = null
            )
        }

    }

    override fun setRecyclerView() {
        if (isParent){
            _recyclerView =
                binding.recyclerViewGridParent.recyclerViewGrid
            viewModel.setAdapter(RecyclerViewAdapterMediatorScroll(
                viewHolderIdSupplier = getHolderIdSupplier(),
                onClickedViewHolder = {
                    fragmentsRouter.router.navigate(PersonageConcreteFragment(it.id))
                     }))
            _recyclerView.adapter = viewModel.adapter
            if (_recyclerView.adapter is EventMediator<*>) {
                listener = _recyclerView.adapter as EventMediator<Scroll>
            }
        }
        else {}

    }

    override fun observeRecyclerView() {
        if(isParent){
            viewModel.observe(listener)
            recyclerView.addOnScrollListener(
                ScrollListener(listener)
            )
        }
        else {}
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
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is OutsourceLogic.UiState.SuccessFetchResult<*> -> {
                    viewModel.adapter.submitList(state.list.map { it.toUiEntity() })
                }
                is OutsourceLogic.UiState.SuccessFetchResultConcrete<*> -> {


                }
                is OutsourceLogic.UiState.BadResult -> { FOC(state) }
                else -> {}
            }

        }
    }

    override fun actAsAChildFragment() {

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
        operator fun invoke(isParent: Boolean = true): PersonagesFragment {
            val bundle = Bundle().apply {
                putBoolean(IS_PARENT, isParent)
            }
            return PersonagesFragment().apply {
                arguments = bundle
            }
        }
    }


}