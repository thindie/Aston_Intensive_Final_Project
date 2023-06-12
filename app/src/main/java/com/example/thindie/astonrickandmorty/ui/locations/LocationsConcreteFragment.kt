package com.example.thindie.astonrickandmorty.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.databinding.FragmentLocationConcreteBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseConcreteFragment
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.UiHolder
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UsesSearchAbleAdaptedRecycleViewAdapter

class LocationsConcreteFragment : BaseConcreteFragment()    {
    val viewModel: LocationsViewModel by lazy { getVM(this) }

    private var _binding: FragmentLocationConcreteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationConcreteBinding.inflate(inflater, container, false)
        return binding.root
    }


}