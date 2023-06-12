package com.example.thindie.astonrickandmorty.ui.personage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonageConcreteBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseConcreteFragment
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.ViewHolderIdSupplier
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.UsesSearchAbleAdaptedRecycleViewAdapter

class PersonageConcreteFragment : BaseConcreteFragment()    {
    val viewModel: PersonagesViewModel by lazy { getVM(this) }


    private var _binding: FragmentPersonageConcreteBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonageConcreteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsRouter.router.dispatchBackPress()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}