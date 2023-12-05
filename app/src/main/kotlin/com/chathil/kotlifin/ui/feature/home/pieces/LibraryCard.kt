package com.chathil.kotlifin.ui.feature.home.pieces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryCard(modifier: Modifier = Modifier, title: String, onClick: () -> Unit = {}) {
    Card(modifier = modifier, onClick = onClick, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.wrapContentSize(),
            )
        }
    }
}

private const val IMAGE_WIDTH = 160
private const val IMAGE_HEIGHT = 90

@Preview
@Composable
private fun CollectionCardPreview() {
    KotlifinTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LibraryCard(modifier = Modifier
                .weight(1f)
                .height(76.dp), "Movies")
            LibraryCard(modifier = Modifier
                .weight(1f)
                .height(76.dp), "TV Shows")
        }
    }
}