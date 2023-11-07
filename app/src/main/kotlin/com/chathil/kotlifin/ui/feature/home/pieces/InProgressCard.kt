package com.chathil.kotlifin.ui.feature.home.pieces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.media.MediaState
import com.chathil.kotlifin.ui.feature.home.mvi.Intent
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@Composable
fun InProgressCard(
    modifier: Modifier = Modifier,
    media: MediaSnippet,
    dispatch: (Intent) -> Unit = {}
) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = KotlifinTheme.dimensions.spacingXS)
        ) {
            AsyncImage(
                model = media.img.constructUrl(
                    InProgressCardImageSize * ImageAspectRatio.second,
                    InProgressCardImageSize * ImageAspectRatio.first,
                ),
                contentDescription = "",
                Modifier.size(
                    width = InProgressCardImageSize * ImageAspectRatio.first.dp,
                    height = InProgressCardImageSize * ImageAspectRatio.second.dp
                )
            )
        }
        Text(
            text = media.title,
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(InProgressCardImageSize * ImageAspectRatio.first.dp)
        )
        if (media is MediaSnippet.Episode) {
            val showSubtitle = "S${media.season}E${media.eps} - ${media.epsTitle}"
            Spacer(modifier = Modifier.height(height = KotlifinTheme.dimensions.spacingXXS))
            Text(
                text = showSubtitle,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.width(InProgressCardImageSize * ImageAspectRatio.first.dp)
            )
        }
    }
}

private const val InProgressCardImageSize = 10
private val ImageAspectRatio = Pair(16, 9)

@Composable
fun InProgressCardLoading(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(
                    width = InProgressCardImageSize * ImageAspectRatio.first.dp,
                    height = InProgressCardImageSize * ImageAspectRatio.second.dp
                )
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingXS))
        Box(
            modifier = Modifier
                .size(
                    width = InProgressCardImageSize / 2 * ImageAspectRatio.first.dp,
                    height = MaterialTheme.typography.titleSmall.lineHeight.value.dp
                )
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.height(height = KotlifinTheme.dimensions.spacingXXS))
        Box(
            modifier = Modifier
                .size(
                    width = InProgressCardImageSize / 3 * ImageAspectRatio.first.dp,
                    height = MaterialTheme.typography.bodySmall.lineHeight.value.dp
                )
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(4.dp))
        )
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun InProgressCardPreview() {
    KotlifinTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            InProgressCard(
                media = MediaSnippet.Movie(
                    id = "",
                    title = "Satan's Alley",
                    state = MediaState(isFavorite = false, isPlayed = false),
                    img = JellyfinImage.Empty
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            InProgressCard(
                media = MediaSnippet.Episode(
                    id = "",
                    title = "New Yellow",
                    state = MediaState(isFavorite = false, isPlayed = false),
                    season = 1,
                    eps = 1,
                    epsTitle = "Yellow Episode",
                    img = JellyfinImage.Empty
                )
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun InProgressCardLoadingPreview() {
    KotlifinTheme {
        InProgressCardLoading(modifier = Modifier.padding(8.dp))
    }
}
