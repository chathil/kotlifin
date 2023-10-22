package com.chathil.kotlifin.ui.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.ui.feature.home.mvi.Intent
import com.chathil.kotlifin.ui.feature.home.mvi.State
import com.chathil.kotlifin.ui.feature.home.pieces.HomeSection
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {},
    goToDetail: () -> Unit = {},
    goToSettings: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            HomeTopAppBar(
                onSettingTapped = goToSettings,
                serverName = state.activeSession.serverName
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            HomeSection(state = state, dispatch = dispatch)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    onSettingTapped: () -> Unit = {},
    serverName: String = ""
) {
    LargeTopAppBar(
        title = {
            Text(text = serverName)
        },
        actions = {
            IconButton(onClick = onSettingTapped) {
                Icon(imageVector = Icons.Rounded.Settings, contentDescription = "settings")
            }
        }
    )
}

const val HomeRoute = "home"

fun NavGraphBuilder.homeScreen(
    goToDetail: () -> Unit = {},
    goToSettings: () -> Unit = {}
) {
    composable(HomeRoute) {

        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.viewStates.collectAsStateWithLifecycle()

        HomeScreen(
            state = state,
            dispatch = viewModel::dispatch,
            goToDetail = goToDetail,
            goToSettings = goToSettings
        )
    }
}

fun NavController.navigateToHomeScreen() {
    navigate(route = HomeRoute)
}

@Preview
@Composable
fun HomeScreenPreview() {
    KotlifinTheme {
        val state = State.Initial.copy(
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
                )
            )
        )
        HomeScreen(state = state)
    }
}