package com.example.thindie.astonrickandmorty.ui.basis

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.thindie.astonrickandmorty.application.RickAndMortyApplication
import com.example.thindie.astonrickandmorty.application.di.AppComponent
import com.example.thindie.astonrickandmorty.ui.FragmentsRouter
import com.example.thindie.astonrickandmorty.ui.MainViewModel
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngine
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineUser
import javax.inject.Inject

abstract class BaseFragment : Fragment(), SearchEngineUser {

    private var _engine: SearchEngine? = null
    val engine
        get() = _engine

    override fun setEngine(engine: SearchEngine) {
        this._engine = engine
    }

    abstract fun actAsAParentFragment()
    abstract fun actAsAChildFragment()

    @Inject
    lateinit var factory: ViewModelFactory

    val viewModel: MainViewModel by activityViewModels { factory }

    private val injector: AppComponent by lazy {
        (
            requireActivity()
                .application as RickAndMortyApplication
            ).appComponent
    }

    protected val searchEngine
        get() = requireActivity() as SearchEngineManager

    protected val fragmentsRouter
        get() = requireActivity() as FragmentsRouter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SearchEngine.inject(this, lifecycleScope)
        injector.inject(this)
    }
}
