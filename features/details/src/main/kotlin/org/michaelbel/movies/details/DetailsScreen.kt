package org.michaelbel.movies.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import org.michaelbel.movies.core.image
import org.michaelbel.movies.core.model.Movie

@Composable
fun DetailsScreen(
    navController: NavController,
    movieId: Long
) {
    val viewModel: DetailsViewModel = hiltViewModel()
    val movie: Movie? by viewModel.movieFlow.collectAsState(initial = null)

    LaunchedEffect(movieId) {
        viewModel.fetchMovieById(movieId)
    }

    Scaffold(
        topBar = { Toolbar(navController, movie?.title.orEmpty()) }
    ) {
        Content(movie)
    }
}

@Composable
private fun Toolbar(
    navController: NavController,
    movieTitle: String
) {
    SmallTopAppBar(
        title = { Text(text = movieTitle) },
        modifier = Modifier.systemBarsPadding(),
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun Content(
    movie: Movie?
) {
    val scrollState: ScrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        val imagePainter = rememberImagePainter(
            data = image(movie?.backdropPath ?: "http://null")
        )
        Image(
            painter = imagePainter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(220.dp)
                .fillMaxSize()
        )
        Text(
            text = movie?.title.orEmpty(),
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
        )
        Text(
            text = movie?.overview.orEmpty(),
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
        )
        Advert()
    }
}

@Composable
private fun Advert(

) {
    val adRequest: AdRequest = AdRequest.Builder().build()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.LARGE_BANNER
                    adUnitId = context.getString(R.string.admobBannerTestUnitId)
                    loadAd(adRequest)
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}