package com.example.thindie.astonrickandmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentEpisodeConcreteBinding
import com.example.thindie.astonrickandmorty.ui.basis.BaseConcreteFragment
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toUiEntity
import com.example.thindie.astonrickandmorty.ui.basis.uiApi.OutsourceLogic

class EpisodesConcreteFragment : BaseConcreteFragment() {
    private val viewModel: EpisodesViewModel by lazy { getVM(this) }


    private var _binding: FragmentEpisodeConcreteBinding? = null
    private val binding get() = _binding!!
    override fun actAsAParentFragment() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeConcreteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentsRouter.router.dispatchBackPress()

        viewModel.viewState.observe(viewLifecycleOwner) { fetchedConcrete ->
            when (fetchedConcrete) {
                is OutsourceLogic.UiState.SuccessFetchResultConcrete<*> -> {
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.episode_concrete_fragment_container, EpisodesFragment())
                        .commit()
                    val fetched = fetchedConcrete.fetchedUnit.toUiEntity<EpisodesUiModel>()
                    viewModel.onConcreteScreenObtainList(fetched.characters)
                    applyConcreteFragmentUi(fetched)
                }
                else -> { /* ignore */ // todo  }
                }

            }
        }
    }

    private fun applyConcreteFragmentUi(fetched: EpisodesUiModel) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}