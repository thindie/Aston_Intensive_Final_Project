package com.example.thindie.astonrickandmorty.ui.personage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonagesBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.Scroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UsesSearchAbleAdaptedRecycleViewAdapter
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchEngineResultConsumer

class PersonagesFragment : BaseFragment(), UsesSearchAbleAdaptedRecycleViewAdapter {


    private var _binding: FragmentPersonagesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonagesViewModel by lazy { getVM(this) }

    private lateinit var listener: EventMediator<Scroll>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsRouter.router.dispatchBackPress()
        viewModel.onClickedNavigationButton()
        searchEngine.observeSearchCriteria()
        setRecyclerView()
        observeRecyclerView()
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is OutsourceLogic.UiState.SuccessFetchResult<*> -> {
                    viewModel.adapter.submitList(it.list.map { it.toUiEntity() })
                }
                else -> {}
            }

        }
    }

    private lateinit var _recyclerView: RecyclerView
    override val recyclerView: RecyclerView
        get() = _recyclerView

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
            viewModel.setAdapter(RecyclerViewAdapter(getHolderIdSupplier()))
            _recyclerView.adapter = viewModel.adapter
            if (_recyclerView.adapter is EventMediator<*>) {
                listener = _recyclerView.adapter as EventMediator<Scroll>
            }
    }

    override fun observeRecyclerView() {
            viewModel.observe(listener)
            recyclerView.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                        if (!this@PersonagesFragment.recyclerView.canScrollVertically(1)) {
                            listener.onEvent()
                        }
                        if (!this@PersonagesFragment.recyclerView.canScrollVertically(-1)) {
                            listener.onEvent()
                        }
                    }

                }
            )
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

    override fun notifyStatusChanged() {
        if (listener.isActive()) listener.setStatus(listener.statusTurnedOff)
        else listener.setStatus(listener.statusActive)
    }

}