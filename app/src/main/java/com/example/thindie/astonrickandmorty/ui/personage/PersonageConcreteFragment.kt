package com.example.thindie.astonrickandmorty.ui.personage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonageConcreteBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseConcreteFragment
import com.example.thindie.astonrickandmorty.ui.basis.ConcreteFragmentTools
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

class PersonageConcreteFragment : BaseConcreteFragment(), ConcreteFragmentTools {


    private val viewModel: PersonagesViewModel by lazy { getVM(this) }


    private var _binding: FragmentPersonageConcreteBinding? = null
    private val binding get() = _binding!!
    override fun actAsAParentFragment() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is OutsourceLogic.UiState.SuccessFetchResultConcrete<*> -> {
                    val stateConcrete = state.fetchedUnit.toUiEntity<PersonagesUiModel>()
                    val episodes = stateConcrete.episode
                    initialiseParent(stateConcrete)
                    initialiseChild(isChild = true)
                }
                else -> {}
            }
        }
    }


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
        actAsAParentFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun initialiseParent(searchAble: SearchAble) {
        with(binding) {
            personageConcreteMajorComponent1.text = searchAble.getMajorComponent()
            personageConcreteMajorComponent2.text = searchAble.getMajorComponent1()
            personageConcreteMajorComponent3.text = searchAble.getMajorComponent2()
        }

    }

    override fun initialiseChild(isChild: Boolean) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.personage_concrete_fragment_container, EpisodesFragment(isChild))
            .commit()
    }
}