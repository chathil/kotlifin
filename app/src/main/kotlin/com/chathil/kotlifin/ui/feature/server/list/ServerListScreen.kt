package com.chathil.kotlifin.ui.feature.server.list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.ui.feature.server.ServerManagementViewModel
import com.chathil.kotlifin.ui.feature.server.mvi.Event
import com.chathil.kotlifin.ui.feature.server.mvi.Intent
import com.chathil.kotlifin.ui.feature.server.mvi.State
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerListScreen(
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {},
    onAddServer: () -> Unit = {}
) {
    Scaffold(topBar = { ServerListTopAppBar(onAddTapped = onAddServer) }) { padding ->
        Column(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(padding)
        ) {
            state.savedServers.forEach { server ->
                ServerCard(server = server, onTapped = { server -> dispatch(Intent.SelectedServer(server)) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServerCard(
    modifier: Modifier = Modifier,
    server: JellyfinServer,
    onTapped: (server: JellyfinServer) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = KotlifinTheme.dimensions.spacingMedium,
                KotlifinTheme.dimensions.spacingSmall
            ),
        onClick = { onTapped(server) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(KotlifinTheme.dimensions.spacingSmall)
        ) {
            Icon(
                modifier = Modifier
                    .padding(KotlifinTheme.dimensions.spacingSmall)
                    .size(KotlifinTheme.dimensions.iconXL),
                imageVector = Icons.Rounded.List,
                contentDescription = "server icon"
            )

            Column(
                modifier = Modifier.padding(end = KotlifinTheme.dimensions.spacingSmall),
                verticalArrangement = Arrangement.spacedBy(KotlifinTheme.dimensions.spacingXXS)
            ) {
                Text(server.name, style = MaterialTheme.typography.titleMedium)
                Text(server.publicAddress, style = MaterialTheme.typography.bodyMedium)
                Text("${server.users.count()} user", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServerListTopAppBar(
    onSettingTapped: () -> Unit = {},
    onAddTapped: () -> Unit = {}
) {
    LargeTopAppBar(
        title = {
            Text(text = "Servers")
        },
        actions = {
            IconButton(onClick = onSettingTapped) {
                Icon(imageVector = Icons.Rounded.Settings, contentDescription = "setting icon")
            }
            IconButton(onClick = onAddTapped) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "add server icon")
            }
        }
    )
}

@Preview
@Composable
fun ServerListScreenPreview() {
    val state = State.Initial.copy(
        savedServers = listOf(
            JellyfinServer.Empty.copy(
                name = "Production",
                publicAddress = "https://prod.jellyfin.org"
            ), JellyfinServer.Empty.copy(
                name = "Staging",
                publicAddress = "https://stage.jellyfin.org"
            ),
            JellyfinServer.Empty.copy(
                name = "Development",
                publicAddress = "https://dev.jellyfin.org"
            )
        )
    )
    KotlifinTheme {
        ServerListScreen(state = state)
    }
}

const val ServerListRoute = "server_list"

fun NavGraphBuilder.serverListScreen(
    viewModel: ServerManagementViewModel,
    onAddServer: () -> Unit = {},
    onSelectUser: (serverId: String) -> Unit = {}
) {
    composable(ServerListRoute) {
        val state by viewModel.viewStates.collectAsStateWithLifecycle()
        LaunchedEffect(Unit) {
            viewModel.viewEvents.collect { event ->
                when(event) {
                    is Event.NavigateToSelectUser -> onSelectUser(event.server.id)
                    is Event.NavigateToSignIn -> {}
                    is Event.NavigateToHome -> {}
                }
            }
        }
        ServerListScreen(state, viewModel::dispatch, onAddServer)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun ServerCardPreview() {
    KotlifinTheme {
        ServerCard(
            server = JellyfinServer.Empty.copy(
                name = "Demo Stable",
                publicAddress = "https://demo.jellyfin.org"
            )
        )
    }
}