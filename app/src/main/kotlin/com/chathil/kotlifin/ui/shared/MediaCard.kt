package com.chathil.kotlifin.ui.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.media.MediaState
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@Composable
fun MediaCard(modifier: Modifier = Modifier, data: MediaSnippet) {
    Column(modifier = modifier) {
        Card(modifier = Modifier.size(posterSize)) {
            AsyncImage(
                model = data.img.constructUrl(
                    MEDIA_CARD_POSTER_SIZE * MEDIA_CARD_ASPECT_RATIO.first,
                    MEDIA_CARD_POSTER_SIZE * MEDIA_CARD_ASPECT_RATIO.second,
                ),
                contentDescription = "",
                Modifier.size(posterSize)
            )
        }
        Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingXXS))
        Text(
            data.title,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            textAlign = TextAlign.Start,
            modifier = Modifier.width(
                posterSize.width
            )
        )
    }
}

const val MEDIA_CARD_POSTER_SIZE = 64
val MEDIA_CARD_ASPECT_RATIO = Pair(2, 3)

private val posterSize = DpSize(
    MEDIA_CARD_POSTER_SIZE * MEDIA_CARD_ASPECT_RATIO.first.dp,
    MEDIA_CARD_POSTER_SIZE * MEDIA_CARD_ASPECT_RATIO.second.dp
)

@Preview(showBackground = true, backgroundColor = 0XFFFFFF)
@Composable
private fun MediaCardPreview() {
    KotlifinTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            MediaCard(
                data = MediaSnippet.Movie(
                    "",
                    "Simple Jack",
                    MediaState.Empty,
                    JellyfinImage.Empty
                )
            )
        }
    }
}
