package com.chathil.kotlifin.ui.shared

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KotlifinSearchBar(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = false,
        onActiveChange = {},
        placeholder = {
            Text("Search Stuff")
        },
        content = {
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun SearchBarPreview() {
    KotlifinTheme {
        KotlifinSearchBar(modifier = Modifier.padding(8.dp))
    }
}