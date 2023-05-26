package com.example.thindie.astonrickandmorty.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable.ComplexRecyclerViewAdapterManager
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable.ComplexUiHolder
import com.example.thindie.astonrickandmorty.ui.basis.recyclerview.complexoperationable.ComplexedRecyclerViewAdapter
import com.example.thindie.astonrickandmorty.ui.uiutils.searchBar.SearchEngineResultConsumer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsViewModel @Inject constructor() :
    ViewModel(),
    ComplexRecyclerViewAdapterManager<ComplexLocationsUiModel, ComplexUiHolder>,
    SearchEngineResultConsumer {

    private lateinit var adapter: ComplexedRecyclerViewAdapter<ComplexLocationsUiModel, ComplexUiHolder>
    private var duplicateList: List<ComplexLocationsUiModel> = emptyList()

    private val _emptySearchResult: MutableLiveData<Boolean> = MutableLiveData(false)
    val emptySearchResult: LiveData<Boolean> get() = _emptySearchResult

    override fun <T : ComplexedRecyclerViewAdapter<ComplexLocationsUiModel, ComplexUiHolder>> getAdapter(): T {
        return adapter as T
    }

    fun isRefreshNeeded() = getNextPool().isBlank() || getPrevPool().isBlank()

    override fun <T : ComplexedRecyclerViewAdapter<ComplexLocationsUiModel, ComplexUiHolder>> setAdapter(
        adapter: T
    ) {
        if (this::adapter.isInitialized == false) this.adapter = adapter
    }

    fun getNextPool(): String {
        return try {
            adapter.currentList.last().uiModel.pool.next
        } catch (_: Exception) {
            ""
        }
    }

    fun getPrevPool(): String {
        return try {
            adapter.currentList.last().uiModel.pool.prev
        } catch (_: Exception) {
            ""
        }
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

    override fun shareActualList(): List<OperationAble> {
        return duplicateList.ifEmpty { return adapter.currentList }
    }

    override fun submitList(modelList: List<ComplexLocationsUiModel>) {
        duplicateList = modelList
        adapter.submitList(duplicateList)
    }
}
