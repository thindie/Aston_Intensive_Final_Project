package com.example.thindie.astonrickandmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentEpisodeConcreteBinding
import com.example.thindie.astonrickandmorty.ui.MainViewModel
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.ConcreteFragmentTools
import com.example.thindie.astonrickandmorty.ui.basis.StubFragment
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class EpisodesConcreteFragment : BaseFragment(), ConcreteFragmentTools {

    private var _binding: FragmentEpisodeConcreteBinding? = null
    private val binding get() = _binding!!
    override fun actAsAParentFragment() = Unit

    override fun actAsAChildFragment() {
        viewModel.concreteScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainViewModel.ConcreteScreenState.ConcreteEpisode -> {
                    initialiseParent(state.episode)
                    viewModel.onObtainListForConcreteEpisodeScreen(state.episode.characters)
                    initialiseChild(Any())
                }
                is MainViewModel.ConcreteScreenState.ShowUnderConstruction -> {
                    showUnderConstruction()
                }

                else -> {
                }
            }
        }
    }

    override fun initialiseParent(searchAble: OperationAble) {
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
                PersonagesFragment.getInstance(openAsChildInstance = true)
            )
            .commit()
    }

    fun showUnderConstruction() {
        childFragmentManager.commit {
            replace(R.id.episode_concrete_fragment_container, StubFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actAsAChildFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeConcreteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getSearchingEngineManager(): SearchEngineManager {
        error("")
    }

    override fun getSearchingConsumer(): SearchEngineResultConsumer {
        error("")
    }

    override fun getSearchAbleList(): List<OperationAble> {
        error("")
    }
}
