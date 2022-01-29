package org.michaelbel.moviemade.repository

import javax.inject.Inject
import org.junit.Before
import org.junit.Test
import org.michaelbel.moviemade.app.data.Api
import org.michaelbel.moviemade.app.domain.repo.MoviesRepository
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

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

    @Test
    fun `when moviesStream called it should get movies`() {


        //val carViewModel = CarViewModel(mockCarRepository)
        //carViewModel.fetchCarsByBrand("bmw")
        //verify(mockCarRepository).getCars()
    }
}