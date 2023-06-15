package com.example.thindie.astonrickandmorty.ui.episodes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentEpisodeConcreteBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseConcreteFragment
import com.example.thindie.astonrickandmorty.ui.basis.FOC
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.ConcreteFragmentTools
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic
import com.example.thindie.astonrickandmorty.ui.personage.PersonageConcreteFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesUiModel
import com.example.thindie.astonrickandmorty.ui.uiutils.SearchAble

class EpisodesConcreteFragment : BaseConcreteFragment(), ConcreteFragmentTools {
    private val viewModel: EpisodesViewModel by lazy { getVM(this) }


    private var _binding: FragmentEpisodeConcreteBinding? = null
    private val binding get() = _binding!!
    override fun actAsAParentFragment() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is OutsourceLogic.UiState.SuccessFetchResultConcrete<*> -> {
                    val concreteOperatedUiModel = state.fetchedUnit.toUiEntity<EpisodesUiModel>()
                    val personages = concreteOperatedUiModel.characters
                    initialiseParent(concreteOperatedUiModel)
                    initialiseChild(personages)
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
        _binding = FragmentEpisodeConcreteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val id = requireNotNull(arguments).getInt( CONCRETE_ID)
        viewModel.onClickConcrete(id)
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
            episodeConcreteMajorComponent1.text = searchAble.getMajorComponent()
            episodeConcreteMajorComponent2.text = searchAble.getMajorComponent1()
            episodeConcreteMajorComponent3.text = searchAble.getMajorComponent2()
        }
    }

    override fun initialiseChild(arguments: Any) {
        childFragmentManager
            .beginTransaction()
            .replace(
                R.id.episode_concrete_fragment_container,
                PersonagesFragment.invoke(true, (arguments as? List<String>).orEmpty())
            )
            .commit()
    }
    companion object {
        private const val CONCRETE_ID = "ID"
        operator fun invoke(id: Int): EpisodesConcreteFragment {
            return EpisodesConcreteFragment().apply {
                arguments = bundleOf(CONCRETE_ID to id)
            }
        }
    }

}