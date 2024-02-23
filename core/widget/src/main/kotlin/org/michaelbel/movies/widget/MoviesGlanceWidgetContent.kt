package org.michaelbel.movies.widget

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import org.michaelbel.movies.widget.ktx.stringResource
import org.michaelbel.movies.widget.theme.GlanceTheme
import org.michaelbel.movies.widget.work.MoviesUpdateWidgetAction
import org.michaelbel.movies.widget.work.MoviesWidgetState

@Composable
internal fun MoviesGlanceWidgetContent(
    glanceWidgetState: MoviesWidgetState,
    modifier: GlanceModifier = GlanceModifier
) {
    when (glanceWidgetState) {
        is MoviesWidgetState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(GlanceTheme.colors.background)
                    .cornerRadius(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = GlanceTheme.colors.onBackground
                )
            }
        }
        is MoviesWidgetState.Content -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(GlanceTheme.colors.background)
                    .cornerRadius(16.dp)
                    .clickable(actionRunCallback<MoviesUpdateWidgetAction>())
            ) {
                Text(
                    text = stringResource(R.string.appwidget_title),
                    modifier = GlanceModifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    style = TextStyle(
                        color = GlanceTheme.colors.onBackground,
                        textAlign = TextAlign.Left,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                LazyColumn(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    items(glanceWidgetState.movies) { movie ->
                        Row(
                            modifier = GlanceModifier
                                .fillMaxWidth()
                                .clickable(actionStartActivity(
                                    Intent(Intent.ACTION_VIEW, "movies://details/${movie.movieId}".toUri()).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    }
                                ))
                        ) {
                            Text(
                                text = movie.movieTitle,
                                modifier = GlanceModifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                style = TextStyle(
                                    color = GlanceTheme.colors.onBackground,
                                    textAlign = TextAlign.Start,
                                    fontSize = 16.sp
                                ),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
        is MoviesWidgetState.Failure -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(GlanceTheme.colors.background)
                    .cornerRadius(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = glanceWidgetState.message,
                    style = TextStyle(
                        color = GlanceTheme.colors.onBackground,
                        fontSize = 16.sp
                    )
                )

                Spacer(
                    modifier = GlanceModifier.height(8.dp)
                )

                Button(
                    text = "Refresh",
                    onClick = actionRunCallback<MoviesUpdateWidgetAction>()
                )
            }
        }
    }
}