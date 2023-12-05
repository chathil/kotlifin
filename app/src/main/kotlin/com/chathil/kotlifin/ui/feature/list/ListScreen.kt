package com.chathil.kotlifin.ui.feature.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.LocalMovies
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Reorder
import androidx.compose.material.icons.rounded.SortByAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.dto.request.movie.MediaRequest
import com.chathil.kotlifin.ui.feature.list.mvi.Intent
import com.chathil.kotlifin.ui.feature.list.mvi.State
import com.chathil.kotlifin.ui.shared.KotlifinSearchBar
import com.chathil.kotlifin.ui.shared.plus
import com.chathil.kotlifin.ui.theme.KotlifinTheme
import com.chathil.kotlifin.ui.utils.AppBarScrollBehaviour
import com.chathil.kotlifin.ui.utils.KotlifinTopAppBar
import com.chathil.kotlifin.ui.utils.TopAppBarDefaults

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ListScreen(
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {},
    goToDetail: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {

    val listState = rememberLazyListState()
//    val directionalLazyListState = rememberDirectionalLazyListState(
//        listState
//    )

//    LaunchedEffect(remember { derivedStateOf { listState.firstVisibleItemIndex } }) {
//        snapshotFlow { listState.firstVisibleItemIndex }
//            .runningFold(emptyList<Int>()) { acc, value ->
//                acc + value
//            }.toList()
//            .map { index -> index  }
//    }


//    val isCollapsed: Boolean by remember {
//        derivedStateOf { listState.firstVisibleItemIndex > 0 }
//    }

    val scrollBehavior =
        AppBarScrollBehaviour.withContentScrollBehavior(rememberTopAppBarState())

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction == 1f
        }
    }

    Scaffold(
//        topBar = { CollapsedTopBar(isCollapsed = isCollapsed && directionalLazyListState.scrollDirection == ScrollDirection.Down) },
        topBar = { ListTopAppBar(scrollBehavior = scrollBehavior, isCollapsed = isCollapsed) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(0.dp)
    ) { _ ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
            contentPadding = WindowInsets.systemBars.asPaddingValues(LocalDensity.current) + PaddingValues(TOP_APP_BAR_CONTAINER_HEIGHT.dp)
        ) {
            items(50) {
                if (it == 0) {
                    Text("Lorem ipsum dolar sit amet first line")
                } else {
                    Text("Lorem ipsum dolar sit amet")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTopAppBar(
    modifier: Modifier = Modifier,
    dispatch: (Intent) -> Unit = {},
    onBackPressed: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior,
    state: State = State.Initial,
    isCollapsed: Boolean
) {
    KotlifinTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "search")
            }
        },
        title = {
            KotlifinSearchBar(
                query = state.mediaRequest.keyword,
                onQueryChange = {},
                onSearch = {}
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(scrolledContainerColor = if (isCollapsed) Color.Transparent else MaterialTheme.colorScheme.background),
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun CollapsedTopBar(modifier: Modifier = Modifier, isCollapsed: Boolean) {
    val color: Color by animateColorAsState(
        if (isCollapsed) Color.Transparent else MaterialTheme.colorScheme.primary,
        label = ""
    )
    Column(
        modifier = modifier
            .background(color)
            .fillMaxWidth()
            .systemBarsPadding()
            .wrapContentHeight()
            .padding(16.dp),
    ) {
        AnimatedVisibility(visible = !isCollapsed) {
            Column {
                Text(text = "Library", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingMedium))
                FilterSection(modifier = Modifier)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterSection(
    modifier: Modifier = Modifier,
    request: MediaRequest = MediaRequest.Initial
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .wrapContentHeight()
            .padding(vertical = KotlifinTheme.dimensions.spacingSmall),
        horizontalArrangement = Arrangement.spacedBy(KotlifinTheme.dimensions.spacingXS)
    ) {
        Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))

        FilterChip(
            selected = request.mediaTypes.contains(MediaType.MOVIE),
            onClick = { /*TODO*/ },
            label = { Text("Movies") },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Movie, contentDescription = "movies") }
        )

        FilterChip(
            selected = request.mediaTypes.contains(MediaType.SHOW),
            onClick = { /*TODO*/ },
            label = { Text("TV Shows") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.LocalMovies,
                    contentDescription = "tv shows"
                )
            }
        )

        FilterChip(
            selected = request.selectedGenres.isNotEmpty(),
            onClick = { /*TODO*/ },
            label = { Text("Genre") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Category,
                    contentDescription = "genre"
                )
            },
        )

        FilterChip(
            selected = true,
            onClick = { /*TODO*/ },
            label = { Text(request.orderBy.rawValue) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Reorder,
                    contentDescription = "order by"
                )
            },
        )

        FilterChip(
            selected = true,
            onClick = { /*TODO*/ },
            label = { Text(request.sortBy.name) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.SortByAlpha,
                    contentDescription = "sort by"
                )
            },
        )

        Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingMedium))
    }
}

// taken from androidx.compose.material3.tokens.TopAppBarSmallTokens
private val TOP_APP_BAR_CONTAINER_HEIGHT = 64

const val ListRoute = "list"
const val ListRouteMediaType = "mediaType/"

fun NavGraphBuilder.listScreen(
    onBackPressed: () -> Unit = {},
    goToDetail: () -> Unit = {},
) {
    composable(
        route = "$ListRoute{${ListRouteMediaType}}",
        arguments = listOf(
            navArgument(ListRouteMediaType) {
                defaultValue = ""
                type = NavType.StringType
            }
        )
    ) {
        val viewModel: ListViewModel = hiltViewModel()
        val state by viewModel.viewStates.collectAsStateWithLifecycle()

        ListScreen(
            state = state,
            dispatch = viewModel::dispatch,
            goToDetail = goToDetail,
            onBackPressed = onBackPressed
        )
    }
}

fun NavController.navigateToListScreen(mediaType: MediaType?) {
    navigate(route = ListRoute + (mediaType?.name ?: ""))
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun ListScreenPreview() {
    KotlifinTheme {
        ListScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun FilterSectionPreview() {
    KotlifinTheme {
        FilterSection()
    }
}