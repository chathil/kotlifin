package com.chathil.kotlifin.ui.feature.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chathil.kotlifin.data.model.user.JellyfinUser
import com.chathil.kotlifin.ui.feature.users.mvi.Event
import com.chathil.kotlifin.ui.feature.users.mvi.Intent
import com.chathil.kotlifin.ui.feature.users.mvi.State
import com.chathil.kotlifin.ui.theme.KotlifinTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            UsersTopAppBar(
                serverName = state.server.name,
                onBackPressed = onBackPressed
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingMedium))
            state.users.forEach { user ->
                ItemUser(
                    user = user,
                    onTapped = { dispatch(Intent.SwitchSession(user, state.server)) }
                )
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UsersTopAppBar(
    serverName: String,
    onBackPressed: () -> Unit = {}
) {
    LargeTopAppBar(
        title = { Text(serverName) },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
            }
        }
    )
}

@Composable
private fun ItemUser(
    modifier: Modifier = Modifier,
    user: JellyfinUser,
    onTapped: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onTapped() } // TODO: add ripple fx
            .padding(
                horizontal = KotlifinTheme.dimensions.spacingMedium,
                vertical = KotlifinTheme.dimensions.spacingSmall
            )
    ) {
        Image(
            imageVector = Icons.Rounded.Image,
            contentDescription = "profile image",
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(KotlifinTheme.dimensions.spacingXS))
        Text(user.name, modifier = Modifier.fillMaxWidth())
    }
}

const val UsersRoute = "users"
const val UsersRouteServerId = "serverId/"

fun NavGraphBuilder.usersScreen(onBackPressed: () -> Unit = {}, goToHome: () -> Unit = {}) {
    composable(
        route = "$UsersRoute{${UsersRouteServerId}}",
        arguments = listOf(
            navArgument(UsersRouteServerId) {
                defaultValue = ""
                type = NavType.StringType
            }
        )
    ) {
        val viewModel: UsersViewModel = hiltViewModel()
        val state by viewModel.viewStates.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.viewEvents.collect {
                if (it is Event.NavigateToHome) {
                    goToHome()
                }
            }
        }

        UsersScreen(state, viewModel::dispatch, onBackPressed)
    }
}

fun NavController.navigateToUsersScreen(id: String) {
    navigate(route = UsersRoute + id)
}

@Preview
@Composable
private fun UsersScreenPreview() {
    KotlifinTheme {
        UsersScreen(
            State.Initial.copy(
                users = listOf(
                    JellyfinUser.Empty.copy(name = "demo one"),
                    JellyfinUser.Empty.copy(name = "demo two"),
                    JellyfinUser.Empty.copy(name = "demo three")
                )
            )
        )
    }
}
