package com.example.thindie.astonrickandmorty.ui.personage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonagesBinding
import com.example.thindie.astonrickandmorty.ui.searchBar.SearchBarControl
import com.example.thindie.astonrickandmorty.ui.searchBar.SearchBarEngine
import com.example.thindie.astonrickandmorty.ui.searchBar.SearchBarProducer

class PersonagesFragment : Fragment(), SearchBarControl {

    private var _binding: FragmentPersonagesBinding? = null
    private val binding get() = _binding!!
    private val personagesViewModel: PersonagesViewModel by viewModels()
    override lateinit var producer: SearchBarEngine
    override val consumer: SearchBarEngine = personagesViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        SearchBarProducer.inject(this)
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
}