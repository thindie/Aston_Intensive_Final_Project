package com.example.thindie.astonrickandmorty.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentLocationConcreteBinding
import com.example.thindie.astonrickandmorty.ui.MainViewModel
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.ConcreteFragmentTools
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class LocationsConcreteFragment : BaseFragment(), ConcreteFragmentTools {

    private var _binding: FragmentLocationConcreteBinding? = null
    private val binding get() = _binding!!
    override fun actAsAParentFragment() = Unit

    override fun actAsAChildFragment() {
        viewModel.concreteScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainViewModel.ConcreteScreenState.ConcreteLocation -> {
                    initialiseParent(state.location)
                    viewModel.onObtainListForConcreteLocationsScreen(state.location.residents)
                    initialiseChild(Any())
                }
                else -> { /* ignore */
                }
            }
        }
    }

    override fun initialiseParent(searchAble: OperationAble) {
        with(binding) {
            locationsConcreteMajorComponent1.text = searchAble.getMajorComponent()
            locationsConcreteMajorComponent2.text = searchAble.getMajorComponent1()
            locationsConcreteMajorComponent3.text = searchAble.getMajorComponent2()
        }
    }

    override fun initialiseChild(arguments: Any) {
        childFragmentManager
            .beginTransaction()
            .replace(
                R.id.location_concrete_fragment_container,
                PersonagesFragment.getInstance(openAsChildInstance = true)
            )
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actAsAChildFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getSearchingEngineManager(): SearchEngineManager {
        error("")
    }

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        error("")
    }

    override fun getSearchAbleList(): List<OperationAble> {
        error("")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationConcreteBinding.inflate(inflater, container, false)
        return binding.root
    }
}
