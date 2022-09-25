package org.michaelbel.movies.details.ui

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.details.R
import org.michaelbel.movies.entities.MovieDetailsData

@Composable
fun DetailsContent(
    navController: NavController,
    movieId: Long
) {
    val viewModel: DetailsViewModel = hiltViewModel()
    val movie: MovieDetailsData by viewModel.movieFlow.collectAsState(MovieDetailsData.EMPTY)

    LaunchedEffect(movieId) {
        viewModel.fetchMovieById(movieId)
    }

    Scaffold(
        topBar = {
            Toolbar(
                navController = navController,
                movieTitle = movie.title)
        }
    ) { paddingValues: PaddingValues ->
        Content(
            paddingValues = paddingValues,
            movie = movie
        )
    }
}

@Composable
private fun Toolbar(
    navController: NavController,
    movieTitle: String
) {
    SmallTopAppBar(
        title = {
            Text(
                text = movieTitle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,

            )
        },
        modifier = Modifier
            .statusBarsPadding(),
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
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
    paddingValues: PaddingValues,
    movie: MovieDetailsData
) {
    val context: Context = LocalContext.current
    val scrollState: ScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(movie.backdropPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .height(220.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(12F)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
        )

        Text(
            text = movie.overview,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
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