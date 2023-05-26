import androidx.lifecycle.Observer
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeDomain
import com.example.thindie.astonrickandmorty.domain.episodes.EpisodeProvider
import com.example.thindie.astonrickandmorty.domain.locations.LocationProvider
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain
import com.example.thindie.astonrickandmorty.domain.personages.PersonageProvider
import com.example.thindie.astonrickandmorty.ui.MainViewModel
import com.example.thindie.astonrickandmorty.ui.episodes.EpisodesUiModel
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify

class MainViewModelTest : BehaviorSpec({

    lateinit var viewModel: MainViewModel

    val episodeProvider: EpisodeProvider = mockk()

    val personageProvider: PersonageProvider = mockk()

    val locationProvider: LocationProvider = mockk()

    val screenStateObserver: Observer<MainViewModel.ScreenState?> = mockk()

    val concreteScreenStateObserver: Observer<MainViewModel.ConcreteScreenState> = mockk()

    val listEpisodes: List<EpisodeDomain> = mockk()
    val listPersonages: List<PersonageDomain> = mockk()

    val personage: PersonageDomain = mockk()

    val listLinks: List<String> = mockk()
    val episodeUiModel: EpisodesUiModel = mockk()

    viewModel = MainViewModel(
        episodeProvider = episodeProvider,
        personageProvider = personageProvider,
        locationsProvider = locationProvider
    )
/*
    viewModel.screenState.observeForever(screenStateObserver)
    viewModel.concreteScreenState.observeForever(concreteScreenStateObserver)
*/

    Given("list of episodes") {
        coEvery {
            episodeProvider.getPoolOf(listLinks)
        } returns Result.success(listEpisodes)

        coEvery { personageProvider.getPoolOf(personage.episode) } returns Result.success(
            listPersonages
        )

        When("onObtainListForConcretePersonageScreen is called") {
            viewModel.onObtainListForConcretePersonageScreen(personage.episode)

            Then("screenState should be Loading()") {
                verify(exactly = 1) {
                    screenStateObserver.onChanged(
                        MainViewModel.ScreenState.Loading()
                    )
                }
            }

            Then("concreteScreenState should be EpisodesScreenState") {
                verify(exactly = 1) {
                    concreteScreenStateObserver.onChanged(
                        MainViewModel.ConcreteScreenState.ConcreteEpisode(
                            episodeUiModel
                        )
                    )
                }
            }
        }
    }
})
