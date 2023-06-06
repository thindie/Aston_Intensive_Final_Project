package com.example.thindie.astonrickandmorty.ui.personage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonagesBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class PersonagesFragment : BaseFragment() {

    private var _binding: FragmentPersonagesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonagesViewModel by lazy { getVM(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsRouter.router.dispatchBackPress()
        searchEngine.observeSearchCriteria()
        /*viewModel.fetchAll()
        viewModel.observable.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.UiState.SuccessFetchResult<*> -> {
                    Log.d("SERVICE", it.list.toString())
                }
                else -> {}
            }*/
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
        error("")
    }

    override fun getSearchAbleList(): List<SearchAble> {
        return emptyList() //todo
    }

}