package com.example.thindie.astonrickandmorty.ui.personage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonageConcreteBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseConcreteFragment
import com.example.thindie.astonrickandmorty.ui.basis.ConcreteFragmentTools
import com.example.thindie.astonrickandmorty.ui.basis.FOC
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

class PersonageConcreteFragment : BaseConcreteFragment(), ConcreteFragmentTools {


    private val viewModel: PersonagesViewModel by lazy { getVM(this) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val id = requireNotNull(arguments).getInt(CONCRETE_ID)
        viewModel.onClickConcrete(id)
    }

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
                is OutsourceLogic.UiState.BadResult -> {
                   FOC(state)
                }
                else -> {
                }
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

    companion object {
        private const val CONCRETE_ID = "ID"
        operator fun invoke(id: Int): PersonageConcreteFragment {
            return PersonageConcreteFragment().apply {
               arguments = bundleOf(CONCRETE_ID to id)
            }
        }
    }
}