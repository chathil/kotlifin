package com.chathil.kotlifin.ui.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.ui.feature.server.ServerManagementRoute
import com.chathil.kotlifin.ui.feature.settings.mvi.Intent
import com.chathil.kotlifin.ui.feature.settings.mvi.State
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {},
    manageServer: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    Scaffold(topBar = { SettingsTopAppBar(onBackPressed = onBackPressed) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = KotlifinTheme.dimensions.spacingMedium)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Active Session")
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))
            ActiveSession(
                activeSession = state.activeSession,
                jellyfinServer = state.currentServer,
                manageServer = manageServer
            )
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingMedium))
            Text("Playback Setting")
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))
            PlayerSettings()
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingMedium))
        }
    }
}

@Composable
private fun ActiveSession(
    modifier: Modifier = Modifier,
    activeSession: ActiveSession,
    jellyfinServer: JellyfinServer,
    manageServer: () -> Unit = {}
) {
    Card(modifier = modifier) {
        Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingMedium))
        ActiveSessionLine(
            icon = Icons.Rounded.Person,
            title = "User",
            content = activeSession.username
        )
        ActiveSessionLine(
            icon = Icons.Rounded.Cloud,
            title = "Server",
            content = jellyfinServer.name
        )
        // TODO: if connected locally, show local url and connected locally sign
        ActiveSessionLine(
            icon = Icons.Rounded.Link,
            title = "Url",
            content = jellyfinServer.publicAddress
        )
        // TODO: need to fetch all server info
        ActiveSessionLine(
            icon = Icons.Rounded.Update,
            title = "Version",
            content = jellyfinServer.version
        )
        ActiveSessionLine(
            icon = Icons.Rounded.Computer,
            title = "OS",
            content = jellyfinServer.operatingSystem
        )
        Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))
        TextButton(
            onClick = manageServer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Switch User")
        }
        Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))
    }
}

@Composable
private fun PlayerSettings(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Text(
            "\uD83D\uDC77 Under Construction",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(KotlifinTheme.dimensions.spacingLarge)
        )
    }
}

@Composable
private fun ActiveSessionLine(
    icon: ImageVector,
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = KotlifinTheme.dimensions.spacingMedium,
                vertical = KotlifinTheme.dimensions.spacingXS
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title)
        Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingXS))
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Text(
            text = content,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTopAppBar(
    onBackPressed: () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back button")
            }
        },
        title = {
            Text("Settings")
        }
    )
}

const val SettingsRoute = "settings"

fun NavGraphBuilder.settingsScreen(
    manageServer: () -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    composable(SettingsRoute) {
        val viewModel: SettingsViewModel = hiltViewModel()
        val state by viewModel.viewStates.collectAsStateWithLifecycle()

        SettingsScreen(
            state = state,
            dispatch = viewModel::dispatch,
            manageServer = manageServer,
            onBackPressed = onBackPressed
        )
    }
}

fun NavController.navigateToSettingsScreen() = navigate(route = SettingsRoute)

@Preview
@Composable
private fun SettingsScreenPreview() {
    KotlifinTheme {
        SettingsScreen()
    }
}

@Preview
@Composable
private fun ActiveSessionPreview() {
    KotlifinTheme {
        ActiveSession(
            activeSession = ActiveSession(
                userId = "",
                username = "demo",
                serverId = "",
                serverPublicAddress = "https://demo.jellyfin.org/unstable",
                serverLocalAddress = "http://192.168.0.101:8096",
                serverName = "Unstable",
                accessToken = ""
            ),
            jellyfinServer = JellyfinServer(
                id = "",
                name = "Unstable",
                publicAddress = "https://demo.jellyfin.org/unstable",
                localAddress = "https://demo.jellyfin.org/unstable",
                version = "10.0.9",
                productName = "",
                operatingSystem = "Ubuntu Server",
                users = listOf()
            )
        )
    }
}

@Preview
@Composable
private fun PlayerSettingsPreview() {
    KotlifinTheme {
        PlayerSettings()
    }
}