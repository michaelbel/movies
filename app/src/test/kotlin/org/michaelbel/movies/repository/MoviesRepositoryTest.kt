package org.michaelbel.movies.repository

import org.junit.Before
import org.junit.Test
import org.michaelbel.moviemade.app.data.Api
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

    //private val mockMoviesRepository: MoviesRepository = mock(MoviesRepository::class.java)

    @Test
    fun `when moviesStream called it should get movies`() {


        //val carViewModel = CarViewModel(mockCarRepository)
        //carViewModel.fetchCarsByBrand("bmw")
        //verify(mockCarRepository).getCars()
    }
}