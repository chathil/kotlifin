package com.chathil.kotlifin.ui.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.dto.request.media.NowWatchingRequest
import com.chathil.kotlifin.data.dto.request.show.ShowNextUpRequest
import com.chathil.kotlifin.data.model.image.JellyfinImage
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.media.MediaState
import com.chathil.kotlifin.ui.feature.home.mvi.Intent
import com.chathil.kotlifin.ui.feature.home.mvi.State
import com.chathil.kotlifin.ui.feature.home.pieces.HomeRetrySection
import com.chathil.kotlifin.ui.feature.home.pieces.LatestSection
import com.chathil.kotlifin.ui.feature.home.pieces.InProgressCard
import com.chathil.kotlifin.ui.feature.home.pieces.InProgressCardLoading
import com.chathil.kotlifin.ui.shared.MediaCard
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
            NowWatchingSection(state = state, dispatch = dispatch)
            ShowNextUpSection(state = state, dispatch = dispatch)
            LatestSection(state = state, dispatch = dispatch)
        }
    }
}

@Composable
private fun NowWatchingSection(
    modifier: Modifier = Modifier,
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {}
) {
    val nowWatchingPagingItem = state.nowWatchingPager?.flow?.collectAsLazyPagingItems()

    LazyRow(modifier = modifier.fillMaxWidth()) {
        item {
            Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
        }

        items(count = nowWatchingPagingItem?.itemCount ?: 0) { idx ->
            nowWatchingPagingItem?.get(idx)?.let { item ->
                InProgressCard(media = item)
                Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
            }
        }

        if (nowWatchingPagingItem?.loadState?.refresh is LoadState.Loading || state.isNowWatchingLoading) {
            items(NOW_WATCHING_PLACEHOLDER_COUNT) {
                InProgressCardLoading()
                Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
            }
        }

        item {
            (
                    nowWatchingPagingItem?.loadState?.refresh as? LoadState.Error
                        ?: nowWatchingPagingItem?.loadState?.append as? LoadState.Error
                    )
                ?.let { error ->
                    HomeRetrySection(error = error.error) {
                        dispatch(
                            Intent.LoadNowWatching(
                                NowWatchingRequest.Initial.copy(
                                    limit = HomeViewModel.LOAD_LIMIT,
                                    prefetchDistance = HomeViewModel.PREFETCH_DISTANCE
                                )
                            )
                        )
                    }
                }
        }
    }
}

@Composable
private fun ShowNextUpSection(
    modifier: Modifier = Modifier, state: State = State.Initial, dispatch: (Intent) -> Unit = {}
) {
    val showNextUpPagingItem = state.showNextUpPager?.flow?.collectAsLazyPagingItems()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = KotlifinTheme.dimensions.spacingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Next Up", style = MaterialTheme.typography.titleMedium)
        TextButton(onClick = { /*TODO*/ }) {
            Text("See All")
        }
    }
    Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))

    LazyRow(modifier = modifier.fillMaxWidth()) {
        item {
            Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
        }

        items(count = showNextUpPagingItem?.itemCount ?: 0) { idx ->
            showNextUpPagingItem?.get(idx)?.let { item ->
                InProgressCard(media = item)

                Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
            }
        }

        if (showNextUpPagingItem?.loadState?.refresh is LoadState.Loading || state.isNowWatchingLoading) {
            items(NOW_WATCHING_PLACEHOLDER_COUNT) {
                InProgressCardLoading()
                Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
            }
        }

        item {
            (
                    showNextUpPagingItem?.loadState?.refresh as? LoadState.Error
                        ?: showNextUpPagingItem?.loadState?.append as? LoadState.Error
                    )
                ?.let { error ->
                    HomeRetrySection(error = error.error) {
                        dispatch(
                            Intent.LoadShowNextUp(
                                ShowNextUpRequest.Initial.copy(
                                    limit = HomeViewModel.LOAD_LIMIT,
                                    prefetchDistance = HomeViewModel.PREFETCH_DISTANCE
                                )
                            )
                        )
                    }
                }
        }
    }
}

private const val NOW_WATCHING_PLACEHOLDER_COUNT = 4

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
    navigate(route = HomeRoute) {
        popUpTo(HomeRoute) {
            inclusive = true
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    KotlifinTheme {
        val state = State.Initial.copy(
            latestMedia = mapOf(
                MediaType.MOVIE to listOf(
                    MediaSnippet.Movie("1", "Simple Jack", MediaState.Empty, JellyfinImage.Empty),
                    MediaSnippet.Movie(
                        "2",
                        "Global Metldown: Melting Again & Again",
                        MediaState.Empty,
                        JellyfinImage.Empty
                    ),
                    MediaSnippet.Movie("3", "Satan's Alley", MediaState.Empty, JellyfinImage.Empty),
                    MediaSnippet.Movie("4", "Fart", MediaState.Empty, JellyfinImage.Empty),
                    MediaSnippet.Movie("5", "Fart 2", MediaState.Empty, JellyfinImage.Empty)
                )
            )
        )
        HomeScreen(state = state)
    }
}