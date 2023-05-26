@file:Suppress("UNCHECKED_CAST")

package com.example.thindie.astonrickandmorty.ui.personage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview.RecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.rootrecyclerview.UiHolder
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.EventMediator
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.RecyclerMediatorScrollManager
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.RecyclerViewAdapterMediatorScroll
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.scrollmediator.Scroll
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class PersonagesViewModel @Inject constructor() :
    ViewModel(),
    SearchEngineResultConsumer,
    RecyclerMediatorScrollManager<PersonagesUiModel, UiHolder> {

    private lateinit var adapter: RecyclerViewAdapterMediatorScroll<PersonagesUiModel, UiHolder>
    private var duplicateList: List<PersonagesUiModel> = emptyList()

    private val _emptySearchResult: MutableLiveData<Boolean> = MutableLiveData(false)
    val emptySearchResult: LiveData<Boolean> get() = _emptySearchResult

    override fun observe(eventStateListener: EventMediator<Scroll>) = Unit

    fun isRefreshNeeded() = getNextPool().isBlank() || getPrevPool().isBlank()
    fun getNextPool(): String {
        return try {
            duplicateList.last().pool.next
        } catch (_: Exception) {
            ""
        }
    }

    fun getPrevPool(): String {
        return try {
            duplicateList.last().pool.prev
        } catch (_: Exception) {
            ""
        }
    }

    override fun <T : RecyclerViewAdapter<PersonagesUiModel, UiHolder>> setAdapter(adapter: T) {
        this.adapter = adapter as RecyclerViewAdapterMediatorScroll<PersonagesUiModel, UiHolder>
    }

    override fun <T : RecyclerViewAdapter<PersonagesUiModel, UiHolder>> getAdapter(): T {
        return this.adapter as T
    }

    override fun onSearchResult(resultList: List<OperationAble>) {
        if (resultList.isEmpty()) {
            _emptySearchResult.value = true
            viewModelScope.launch {
                delay(2000)
                adapter.submitList(duplicateList)
                _emptySearchResult.value = false
            }
        }
        adapter.submitList(resultList.map { it.getReified() })
    }

    override fun submitList(modelList: List<PersonagesUiModel>) {
        duplicateList = modelList
        adapter.submitList(duplicateList)
    }

    override fun shareActualList(): List<OperationAble> {
        return duplicateList.ifEmpty { return adapter.currentList }
    }
}
