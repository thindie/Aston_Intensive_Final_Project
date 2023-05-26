package com.example.thindie.astonrickandmorty.ui

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeProvider
import com.example.thindie.astonrickandmorty.domain.locations.LocationProvider
import com.example.thindie.astonrickandmorty.domain.personages.PersonageProvider
import com.example.thindie.astonrickandmorty.ui.basis.EventConsumer
import com.example.thindie.astonrickandmorty.ui.basis.mappers.mapEpisodesDomainToUiModel
import com.example.thindie.astonrickandmorty.ui.basis.mappers.mapPersonageDomainToUiModel
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toLocationUiModel
import com.example.thindie.astonrickandmorty.ui.basis.mappers.toPersonagesUiModel
import com.example.thindie.astonrickandmorty.ui.basis.operationables.OperationAble
import com.example.thindie.astonrickandmorty.ui.basis.operationables.WiderOperationAble
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesFragment
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesUiModel
import com.example.thindie.astonrickandmorty.ui.locations.LocationsFragment
import com.example.thindie.astonrickandmorty.ui.locations.LocationsUiModel
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesFragment
import com.example.thindie.astonrickandmorty.ui.personage.PersonagesUiModel
import com.example.thindie.astonrickandmorty.ui.uiutils.colorAwait
import com.example.thindie.astonrickandmorty.ui.uiutils.colorReady
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val episodeProvider: EpisodeProvider,
    private val personageProvider: PersonageProvider,
    private val locationsProvider: LocationProvider
) : ViewModel(), EventConsumer<String> {

    private var isArrowStateShouldShow: Boolean = false

    private val _rootUiState: MutableLiveData<RootUiState> = MutableLiveData()
    val rootUiState: LiveData<RootUiState>
        get() = _rootUiState

    private val _screenState: MutableLiveData<ScreenState?> = MutableLiveData()
    val screenState: LiveData<ScreenState?>
        get() = _screenState

    private val _concreteScreenState: MutableLiveData<ConcreteScreenState> = MutableLiveData()
    val concreteScreenState: LiveData<ConcreteScreenState>
        get() = _concreteScreenState

    private var savedStateFragment: Class<out Fragment>? = null

    private var lastVisitedRootFragment: Class<out Fragment>? = null

    fun showArrowNot() {
        isArrowStateShouldShow = false
        _rootUiState.value = RootUiState.activeStateMockArrow
    }

    fun onTriggerSectionPersonage(url: String? = null) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            _rootUiState.value = RootUiState.mockAllState

            val bufferPersonageList = mutableListOf<PersonagesUiModel>()
            personageProvider.getAll(url)
                .onSuccess { list ->
                    bufferPersonageList
                        .addAll(list.map(mapPersonageDomainToUiModel))
                }
                .onFailure {
                    _screenState.value = ScreenState.SomeVeryBadSituation
                    return@launch
                }

            val screenStateObtainingList =
                bufferPersonageList
                    .additionOperations(
                        fetcher = { Result.success(mockListDueNoNeed()) },
                        mapper = mapEpisodesDomainToUiModel
                    )
                    .getReifiedList<PersonagesUiModel, EpisodesUiModel>()

            _screenState.value = ScreenState
                .PersonagesScreenState(holder = StateHolder(screenUnit = screenStateObtainingList))

            _rootUiState.value = RootUiState(
                isArrowStateShouldShow = false,
                isElementsActive = true
            )
        }
    }

    fun onTriggerSectionEpisode(url: String? = null) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            _rootUiState.value = RootUiState.mockAllState

            val bufferEpisodeList = mutableListOf<EpisodesUiModel>()
            episodeProvider.getAll(url)
                .onSuccess { list ->
                    bufferEpisodeList
                        .addAll(list.map(mapEpisodesDomainToUiModel))
                }
                .onFailure {
                    _screenState.value = ScreenState.SomeVeryBadSituation
                    return@launch
                }

            val screenStateObtainingList =
                bufferEpisodeList
                    .additionOperations(
                        fetcher = { links -> personageProvider.getPoolOf(links) },
                        mapper = mapPersonageDomainToUiModel
                    )
                    .getReifiedList<EpisodesUiModel, PersonagesUiModel>()

            _screenState.value = ScreenState
                .EpisodesScreenState(holder = StateHolder(screenUnit = screenStateObtainingList))
            _rootUiState.value = RootUiState(
                isArrowStateShouldShow = isArrowStateShouldShow,
                isElementsActive = true
            )
        }
    }

    fun onTriggerSectionLocation(url: String? = null) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            _rootUiState.value = RootUiState.mockAllState

            val bufferLocationsList = mutableListOf<LocationsUiModel>()
            locationsProvider.getAll(url)
                .onSuccess { list ->
                    bufferLocationsList
                        .addAll(list.map { it.toLocationUiModel() })
                }
                .onFailure {
                    _screenState.value = ScreenState.SomeVeryBadSituation
                    return@launch
                }

            val screenStateObtainingList =
                bufferLocationsList
                    .map { locationUiModel ->
                        val personagesUiModelList = mutableListOf<PersonagesUiModel>()

                        personageProvider
                            .getPoolOf(locationUiModel.residents)
                            .onSuccess { domainList ->
                                domainList.map { it.toPersonagesUiModel() }
                                    .apply {
                                        personagesUiModelList.addAll(this)
                                    }
                            }
                            .onFailure {
                                _screenState.value = ScreenState.BadResult(it)
                            }
                        locationUiModel to personagesUiModelList.toList()
                    }
            _screenState.value = ScreenState
                .LocationsScreenState(holder = StateHolder(screenUnit = screenStateObtainingList))

            _rootUiState.value = RootUiState(
                isArrowStateShouldShow = isArrowStateShouldShow,
                isElementsActive = true
            )
        }
    }

    fun onTriggerConcreteLocation(locationsUiModel: LocationsUiModel) {
        _concreteScreenState.value = ConcreteScreenState.ConcreteLocation(locationsUiModel)
    }

    fun onTriggerConcreteEpisode(episodesUiModel: EpisodesUiModel) {
        _concreteScreenState.value = ConcreteScreenState.ConcreteEpisode(episodesUiModel)
    }

    fun onTriggerConcretePersonage(personageUiModel: PersonagesUiModel) {
        _concreteScreenState.value = ConcreteScreenState.ConcretePersonages(personageUiModel)
    }

    fun onClickOperationAbleLastKnownLocation(url: String) {
        viewModelScope.launch {
            locationsProvider.getLastKnowLocation(url)
                .onSuccess { value ->
                    onTriggerConcreteLocation(value.toLocationUiModel())
                }
                .onFailure {
                    // todo(
                }
        }
    }

    fun onClickOperationAbleOriginLocation(url: String) {
        onClickOperationAbleLastKnownLocation(url)
    }

    fun onObtainListForConcretePersonageScreen(links: List<String>) {
        isArrowStateShouldShow = true
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading()
            _rootUiState.value = RootUiState.mockAllState
            val bufferPersonageList = mutableListOf<EpisodesUiModel>()
            episodeProvider.getPoolOf(links)
                .onSuccess { list ->
                    bufferPersonageList
                        .addAll(list.map(mapEpisodesDomainToUiModel))
                }

            val screenStateObtainingList =
                bufferPersonageList
                    .additionOperations(
                        fetcher = { links -> personageProvider.getPoolOf(links) },
                        mapper = mapPersonageDomainToUiModel
                    )
                    .getReifiedList<EpisodesUiModel, PersonagesUiModel>()

            _screenState.value = ScreenState
                .EpisodesScreenState(holder = StateHolder(screenUnit = screenStateObtainingList))
            _rootUiState.value = RootUiState(
                isArrowStateShouldShow = isArrowStateShouldShow,
                isElementsActive = true
            )
        }
    }

    fun onObtainListForConcreteLocationsScreen(links: List<String>) {
        isArrowStateShouldShow = true
        viewModelScope.launch {
            val screenStateObtainingList =
                async { fetchFrequentObtainCombination(links) }

            _rootUiState.value = RootUiState.mockAllState
            _screenState.value = ScreenState
                .PersonagesScreenState(holder = StateHolder(screenUnit = screenStateObtainingList.await()))
            _rootUiState.value = RootUiState(
                isArrowStateShouldShow = isArrowStateShouldShow,
                isElementsActive = true
            )
        }
    }

    fun onObtainListForConcreteEpisodeScreen(links: List<String>) {
        isArrowStateShouldShow = true
        viewModelScope.launch {
            val screenStateObtainingList =
                async { fetchFrequentObtainCombination(links) }

            _screenState.value = ScreenState.Loading()
            _rootUiState.value = RootUiState.mockAllState
            _screenState.value = ScreenState
                .PersonagesScreenState(holder = StateHolder(screenUnit = screenStateObtainingList.await()))
            _rootUiState.value = RootUiState(
                isArrowStateShouldShow = isArrowStateShouldShow,
                isElementsActive = true
            )
        }
    }

    private suspend fun fetchFrequentObtainCombination(links: List<String>): List<Pair<PersonagesUiModel, List<EpisodesUiModel>>> {
        _screenState.value = ScreenState.Loading()
        val bufferEpisodeList = mutableListOf<PersonagesUiModel>()
        personageProvider.getPoolOf(links)
            .onSuccess { list ->
                bufferEpisodeList
                    .addAll(list.map(mapPersonageDomainToUiModel))
            }

        return bufferEpisodeList
            .additionOperations(
                fetcher = { Result.success(mockListDueNoNeed()) },
                mapper = mapEpisodesDomainToUiModel
            )
            .getReifiedList()
    }

    private fun <T> mockListDueNoNeed(): List<T> {
        return emptyList()
    }

    private suspend fun <R> List<WiderOperationAble>.additionOperations(
        fetcher: suspend (links: List<String>) -> Result<List<R>>,
        mapper: (R) -> WiderOperationAble
    ): List<Pair<OperationAble, List<OperationAble>>> {
        return map { model ->
            val additionOperationAbleList = mutableListOf<WiderOperationAble>()

            fetcher.invoke(model.getWideComponent())
                .onSuccess { fetchedList ->
                    fetchedList
                        .map { mapper(it) }
                        .apply { additionOperationAbleList.addAll(this) }
                }
                .onFailure {
                    _screenState.value = ScreenState.BadResult(it)
                }
            model to additionOperationAbleList.toList()
        }
    }

    private fun <R : OperationAble, T : OperationAble>
        List<Pair<OperationAble, List<OperationAble>>>.getReifiedList(): List<Pair<R, List<T>>> {
        return map { operationAblePair ->
            Pair(
                first = operationAblePair.first.getReified(),
                second = operationAblePair.second.map { it.getReified() }
            )
        }
    }

    override fun <R> onEvent(clazz: Class<R>, t: String) {
        if (t.isNotBlank()) {
            when (clazz) {
                LocationsFragment::class.java -> {
                    onTriggerSectionLocation(t)
                }
                EpisodesFragment::class.java -> {
                    onTriggerSectionEpisode(t)
                }
                PersonagesFragment::class.java -> {
                    onTriggerSectionPersonage(t)
                }
            }
        }
    }

    fun rememberCurrentScreen(currentFragment: Class<out Fragment>?) {
        savedStateFragment = currentFragment
    }

    fun restoreLastSavedScreen(): Class<out Fragment>? {
        return savedStateFragment
    }

    fun rememberLastRootScreen(lastRootScreen: Class<out Fragment>?) {
        lastVisitedRootFragment = lastRootScreen
    }

    fun getLastVisitedRootFragment(): Class<out Fragment> {
        showArrowNot()
        return lastVisitedRootFragment ?: PersonagesFragment::class.java
    }

    sealed class ScreenState {

        fun show(view: View) {
            view.visibility = View.VISIBLE
        }

        fun hide(view: View) {
            view.visibility = View.INVISIBLE
        }

        data class EpisodesScreenState(
            val holder: StateHolder
        ) : ScreenState()

        data class LocationsScreenState(
            val holder: StateHolder
        ) : ScreenState()

        data class PersonagesScreenState(
            val holder: StateHolder
        ) : ScreenState()

        data class Loading(val unit: Unit = Unit) : ScreenState()

        data class BadResult(val e: Throwable) : ScreenState()

        object SomeVeryBadSituation : ScreenState()
    }

    data class RootUiState(
        val isArrowStateShouldShow: Boolean = false,
        val isElementsActive: Boolean = false
    ) {
        fun show(view: View) {
            view.visibility = View.VISIBLE
        }

        fun hide(view: View) {
            view.visibility = View.INVISIBLE
        }

        fun availAble(view: View) {
            view.isClickable = true
            if (view is ImageButton || view is ImageView) {
                view as ImageView
                view.colorReady()
            }
        }

        fun locked(view: View) {
            view.isClickable = false
            if (view is ImageButton || view is ImageView) {
                view as ImageView
                view.colorAwait()
            }
        }

        companion object {
            val mockAllState = RootUiState()
            val activeStateMockArrow = RootUiState(
                isArrowStateShouldShow = false,
                isElementsActive = true
            )
        }
    }

    fun onPopulateThatResultIsEmpty() {
        _screenState.value = ScreenState.BadResult(IllegalStateException())
        _concreteScreenState.value = ConcreteScreenState.ShowUnderConstruction
    }

    data class StateHolder(
        val timeStamp: Long = System.currentTimeMillis(),
        val screenUnit: List<Pair<OperationAble, List<OperationAble>>>,
    )

    sealed class ConcreteScreenState {

        data class ConcretePersonages(val personage: PersonagesUiModel) : ConcreteScreenState()
        data class ConcreteEpisode(val episode: EpisodesUiModel) : ConcreteScreenState()
        data class ConcreteLocation(val location: LocationsUiModel) : ConcreteScreenState()
        object ShowUnderConstruction : ConcreteScreenState()
    }
}
