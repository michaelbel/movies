package org.michaelbel.moviemade.repository

import androidx.lifecycle.SavedStateHandle
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.michaelbel.moviemade.data.Api
import org.michaelbel.moviemade.data.model.Movie
import org.michaelbel.moviemade.domain.repo.MoviesRepository
import org.michaelbel.moviemade.presentation.features.main.MainViewModel
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import javax.inject.Inject

class MoviesRepositoryTest {

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
    }

    @InjectMocks
    lateinit var api: Api

    @InjectMocks
    lateinit var inAppUpdate: InAppUpdate

    @Inject
    lateinit var moviesRepository: MoviesRepository

    //private val mockMoviesRepository: MoviesRepository = mock(MoviesRepository::class.java)

    private fun getMainViewModel(): MainViewModel {
        val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
        val list: String = Movie.NOW_PLAYING
        val repository = MoviesRepository(api)

        return MainViewModel(
            savedStateHandle = savedStateHandle,
            list = list,
            repository = repository,
            inAppUpdate = inAppUpdate
        )
    }

    @Test
    fun `when moviesStream called it should get movies`() {
        val viewModel: MainViewModel = getMainViewModel()


        //val carViewModel = CarViewModel(mockCarRepository)
        //carViewModel.fetchCarsByBrand("bmw")
        //verify(mockCarRepository).getCars()
    }
}