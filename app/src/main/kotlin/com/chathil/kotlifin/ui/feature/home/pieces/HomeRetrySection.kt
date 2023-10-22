package com.chathil.kotlifin.ui.feature.home.pieces

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chathil.kotlifin.data.dto.request.media.LatestMediaRequest
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.ui.feature.home.mvi.Intent
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_ASPECT_RATIO
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_POSTER_SIZE
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@Composable
fun HomeRetrySection(
    mediaType: MediaType,
    error: Throwable,
    dispatch: (Intent) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .defaultMinSize(minHeight = (MEDIA_CARD_POSTER_SIZE * MEDIA_CARD_ASPECT_RATIO.second).dp)
            .fillMaxWidth()
            .padding(KotlifinTheme.dimensions.spacingMedium),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
    ) {
        Text(
            text = error.message ?: "Something went wrong",
            modifier = Modifier.padding(
                horizontal = KotlifinTheme.dimensions.spacingMedium,
                vertical = KotlifinTheme.dimensions.spacingSmall
            )
        )
        OutlinedButton(
            onClick = { dispatch(Intent.LoadLatestMedia(LatestMediaRequest(mediaType))) },
            modifier = Modifier.padding(KotlifinTheme.dimensions.spacingMedium),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onErrorContainer)
        ) {
            Text("Retry")
        }
    }
}

@Preview
@Composable
private fun HomeRetrySectionPreview() {
    KotlifinTheme {
        HomeRetrySection(
            mediaType = MediaType.MOVIE,
            error = NullPointerException("This isn't happening")
        )
    }
}