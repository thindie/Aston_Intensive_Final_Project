package com.example.thindie.astonrickandmorty.ui.personage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import coil.load
import com.example.thindie.astonrickandmorty.R
import com.example.thindie.astonrickandmorty.databinding.FragmentPersonageConcreteBinding
import com.example.thindie.astonrickandmorty.ui.MainViewModel
import com.example.thindie.astonrickandmorty.ui.basis.BaseFragment
import com.example.thindie.astonrickandmorty.ui.basis.ConcreteFragmentTools
import com.example.thindie.astonrickandmorty.ui.basis.StubFragment
import com.example.thindie.astonrickandmorty.ui.basis.operationables.MinorComponent
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsConcreteFragment
import com.example.thindie.astonrickandmorty.ui.uiutils.organise
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer

class PersonageConcreteFragment : BaseFragment(), ConcreteFragmentTools {

    private var _binding: FragmentPersonageConcreteBinding? = null
    private val binding get() = _binding!!
    override fun actAsAParentFragment() = Unit

    override fun actAsAChildFragment() {
        viewModel.concreteScreenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainViewModel.ConcreteScreenState.ConcretePersonages -> {
                    initialiseParent(state.personage)
                    viewModel.onObtainListForConcretePersonageScreen(state.personage.episode)
                    initialiseChild(state.personage.episode)
                }
                is MainViewModel.ConcreteScreenState.ShowUnderConstruction -> {
                    showUnderConstruction()
                }
                else -> { /* ignore */
                }
            }
        }
    }

    fun showUnderConstruction() {
        childFragmentManager.commit {
            replace(R.id.personage_concrete_fragment_container, StubFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPersonageConcreteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actAsAChildFragment()
        actAsAParentFragment()
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

    override fun initialiseParent(searchAble: OperationAble) {
        with(binding) {
            val model: PersonagesUiModel = searchAble.getReified()
            personageConcreteMajorComponent1.text = searchAble.getMajorComponent()
            personageConcreteMajorComponent2.text = searchAble.getMajorComponent1()
            personageConcreteMajorComponent3.text = searchAble.getMajorComponent2()

            val location = personageConcreteMajorComponent4Clickable

            location.organise(
                string = model.location.name,
                url = model.location.url
            ) { url ->
                fragmentsRouter.navigate(LocationsConcreteFragment(), true)
                viewModel.onClickOperationAbleLastKnownLocation(url)
            }

            val origin = personageConcreteMajorComponent5Clickable
            origin.organise(string = model.origin.name, url = model.origin.url) { url ->
                fragmentsRouter.navigate(LocationsConcreteFragment(), true)
                viewModel.onClickOperationAbleOriginLocation(url)
            }
            val component = searchAble.getMinorComponent()
            val component1 = searchAble.getMinorComponent1()
            if (component is MinorComponent.ImageUrlHolder) {
                component.extract({ personageConcreteImageComponent!! }) {
                    load(it) {
                        allowConversionToBitmap(true)
                    }
                }
            }
            if (component1 is MinorComponent.ImageUrlHolder) {
                component1.extract({ personageConcreteImageComponent!! }) {
                    load(it) {
                        allowConversionToBitmap(true)
                    }
                }
            }
        }
    }

    override fun initialiseChild(arguments: Any) {
        childFragmentManager
            .beginTransaction()
            .replace(
                R.id.personage_concrete_fragment_container,
                EpisodesFragment.getInstance(isChild = true)
            )
            .commit()
    }
}
