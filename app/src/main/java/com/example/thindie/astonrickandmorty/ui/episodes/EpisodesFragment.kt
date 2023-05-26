package com.example.thindie.astonrickandmorty.ui.episodes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.thindie.astonrickandmorty.databinding.FragmentEpisodesBinding
import com.example.thindie.astonrickandmorty.ui.searchBar.SearchBarControl
import com.example.thindie.astonrickandmorty.ui.searchBar.SearchBarEngine
import com.example.thindie.astonrickandmorty.ui.searchBar.SearchBarProducer


class EpisodesFragment : Fragment(), SearchBarControl {

    private var _binding: FragmentEpisodesBinding? = null
    private val binding get() = _binding!!
    private val episodesViewModel: EpisodesViewModel by viewModels()
    override lateinit var producer: SearchBarEngine
    override val consumer: SearchBarEngine = episodesViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        SearchBarProducer.inject(this)
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

}