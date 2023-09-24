package com.chathil.kotlifin.ui.feature.home.pieces

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.Pager
import androidx.paging.compose.collectAsLazyPagingItems
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.ui.shared.MediaCard
import com.chathil.kotlifin.ui.theme.KotlifinTheme
import com.chathil.kotlifin.utils.asPagerData

@Composable
fun HomeSection(modifier: Modifier = Modifier, data: Map<MediaType, List<MediaSnippet>>) {
    Column(modifier = modifier) {
        // TODO: Make this generic
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = KotlifinTheme.dimensions.spacingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Latest Movies")
            Text("See All")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
            data[MediaType.MOVIE]?.forEach { item ->
                MediaCard(data = item)
                Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = KotlifinTheme.dimensions.spacingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Latest TV Shows")
            Text("See All")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
            data[MediaType.TV_SHOW]?.forEach { item ->
                MediaCard(data = item)
                Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
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
                data = mapOf(
                    MediaType.MOVIE to listOf(
                        MediaSnippet("1", "Simple Jack", JellyfinImage.Empty),
                        MediaSnippet("2", "Global Metldown: Melting Again & Again", JellyfinImage.Empty),
                        MediaSnippet("3", "Satan's Alley", JellyfinImage.Empty),
                        MediaSnippet("4", "Fart", JellyfinImage.Empty),
                        MediaSnippet("5", "Fart 2", JellyfinImage.Empty)
                    ),
                    MediaType.TV_SHOW to listOf(
                        MediaSnippet("1", "Simple Jack", JellyfinImage.Empty),
                        MediaSnippet("2", "Global Metldown: Melting Again & Again", JellyfinImage.Empty),
                        MediaSnippet("3", "Satan's Alley", JellyfinImage.Empty),
                        MediaSnippet("4", "Fart", JellyfinImage.Empty),
                        MediaSnippet("5", "Fart 2", JellyfinImage.Empty)
                    )
                )
            )
        }
    }
}
