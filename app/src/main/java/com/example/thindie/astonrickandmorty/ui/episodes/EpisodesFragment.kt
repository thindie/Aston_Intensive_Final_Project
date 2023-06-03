package com.example.thindie.astonrickandmorty.ui.episodes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.thindie.astonrickandmorty.databinding.FragmentEpisodesBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchAble
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer


class EpisodesFragment : BaseFragment() {


    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    private var viewModel: EpisodesViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsRouter.router.dispatchBackPress()
        searchEngine.observeSearchCriteria()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (viewModel == null) viewModel =
            ViewModelProvider(this)[EpisodesViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        return approve(viewModel, this)
    }

    override fun getSearchAbleList(): List<SearchAble> {
        return emptyList() //todo(
    }

}