package com.chathil.kotlifin.ui.feature.home.pieces

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.ui.feature.home.mvi.Intent
import com.chathil.kotlifin.ui.feature.home.mvi.State
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_ASPECT_RATIO
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_POSTER_SIZE
import com.chathil.kotlifin.ui.shared.MediaCard
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {}
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // TODO: Make this generic
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = KotlifinTheme.dimensions.spacingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Latest Movies", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { /*TODO*/ }) {
                Text("See All")
            }
        }
        Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))

        AnimatedContent(
            targetState = state.isMediaLoading[MediaType.MOVIE] == false,
            label = "latest media loading"
        ) { isNotLoading ->
            if (isNotLoading) {
                val mediaErr = state.latestMediaLoadError[MediaType.MOVIE]
                if (mediaErr == null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
                        state.latestMedia[MediaType.MOVIE]?.forEach { item ->
                            MediaCard(data = item)
                            Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
                        }
                    }
                } else {
                    HomeRetrySection(mediaType = MediaType.MOVIE, error = mediaErr, dispatch = dispatch)
                }
            } else {
                Box(
                    modifier = Modifier.height((MEDIA_CARD_POSTER_SIZE * MEDIA_CARD_ASPECT_RATIO.second).dp),
                    contentAlignment = Alignment.Center
                ) {
                    LinearProgressIndicator(modifier = Modifier.padding(vertical = KotlifinTheme.dimensions.spacingMedium))
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = KotlifinTheme.dimensions.spacingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Latest TV Shows", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { /*TODO*/ }) {
                Text("See All")
            }
        }
        Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))

        AnimatedContent(
            targetState = state.isMediaLoading[MediaType.TV_SHOW] == false,
            label = "latest media loading"
        ) { isNotLoading ->
            if (isNotLoading) {
                val mediaErr = state.latestMediaLoadError[MediaType.TV_SHOW]
                if (mediaErr == null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
                        state.latestMedia[MediaType.TV_SHOW]?.forEach { item ->
                            MediaCard(data = item)
                            Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
                        }
                    }
                } else {
                    HomeRetrySection(mediaType = MediaType.TV_SHOW, error = mediaErr, dispatch = dispatch)
                }
            } else {
                Box(
                    modifier = Modifier.height((MEDIA_CARD_POSTER_SIZE * MEDIA_CARD_ASPECT_RATIO.second).dp),
                    contentAlignment = Alignment.Center
                ) {
                    LinearProgressIndicator(modifier = Modifier.padding(vertical = KotlifinTheme.dimensions.spacingMedium))
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeSectionPreview() {
    KotlifinTheme {
        Box {
            HomeSection(
                state = State.Initial.copy(
                    isMediaLoading = mapOf(
                        MediaType.MOVIE to true,
                        MediaType.TV_SHOW to false
                    ),
                    latestMedia = mapOf(
                        MediaType.MOVIE to listOf(
                            MediaSnippet("1", "Simple Jack", JellyfinImage.Empty),
                            MediaSnippet(
                                "2",
                                "Global Metldown: Melting Again & Again",
                                JellyfinImage.Empty
                            ),
                            MediaSnippet("3", "Satan's Alley", JellyfinImage.Empty),
                            MediaSnippet("4", "Fart", JellyfinImage.Empty),
                            MediaSnippet("5", "Fart 2", JellyfinImage.Empty)
                        ),
                        MediaType.TV_SHOW to listOf(
                            MediaSnippet("1", "Simple Jack", JellyfinImage.Empty),
                            MediaSnippet(
                                "2",
                                "Global Metldown: Melting Again & Again",
                                JellyfinImage.Empty
                            ),
                            MediaSnippet("3", "Satan's Alley", JellyfinImage.Empty),
                            MediaSnippet("4", "Fart", JellyfinImage.Empty),
                            MediaSnippet("5", "Fart 2", JellyfinImage.Empty)
                        )
                    )
                )
            )
        }
    }
}
