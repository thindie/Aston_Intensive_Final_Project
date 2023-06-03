package com.example.thindie.astonrickandmorty.ui.basis

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.thindie.astonrickandmorty.application.FragmentsRouter
import com.example.thindie.astonrickandmorty.application.RickAndMortyApplication
import com.example.thindie.astonrickandmorty.application.di.AppComponent
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngine
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineManager
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineUser
import javax.inject.Inject

abstract class BaseFragment : Fragment(), SearchEngineUser {


    @Inject
    lateinit var factory: ViewModelFactory

    private val injector: AppComponent by lazy {
        (requireActivity()
            .application as RickAndMortyApplication).appComponent
    }

    private var _searchEngine: SearchEngine? = null
    protected val searchEngine
        get() = _searchEngine!!

    private var _activity: FragmentActivity? = null
    private val baseActivity
        get() = _activity!!

    private val _fragmentsRouter: FragmentsRouter? by lazy { baseActivity as? FragmentsRouter }
    protected val fragmentsRouter
        get() = _fragmentsRouter!!

    private val _searchManager: SearchEngineManager? by lazy { baseActivity as? SearchEngineManager }
    private val searchManager
        get() = _searchManager!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _activity = this.requireActivity()
        SearchEngine.inject(this)
        injector.inject(this)
    }


    override fun getSearchingEngineManager(): SearchEngineManager {
        return searchManager
    }


    override fun setEngine(engine: SearchEngine) {
        if (_searchEngine == null) _searchEngine = engine
    }

    protected inline fun <reified T : ViewModel> getVM(owner: ViewModelStoreOwner): T {
        return ViewModelProvider(owner, factory)[T::class.java]
    }

}

