package com.chathil.kotlifin.ui.feature.server.add

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chathil.kotlifin.ui.feature.server.ServerManagementViewModel
import com.chathil.kotlifin.ui.feature.server.list.ServerListRoute
import com.chathil.kotlifin.ui.feature.server.mvi.Event
import com.chathil.kotlifin.ui.feature.server.mvi.Intent
import com.chathil.kotlifin.ui.feature.server.mvi.State
import com.chathil.kotlifin.ui.feature.server.signin.navigateToSignIn
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_ASPECT_RATIO
import com.chathil.kotlifin.ui.shared.MEDIA_CARD_POSTER_SIZE
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AddServerScreen(
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = { AddServerTopAppBar(onBackPressed) }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(KotlifinTheme.dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Connect to a Jellyfin Server",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingXS))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.address,
                onValueChange = { address -> dispatch(Intent.UpdateAddress(address)) },
                singleLine = true,
                placeholder = { Text("Server URL") })
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))
            AnimatedContent(
                targetState = state.isLoading,
                label = "sign in animation"
            ) { isLoading ->
                if (isLoading) {
                    Box(
                        modifier = Modifier.height(ButtonDefaults.MinHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        LinearProgressIndicator(modifier = Modifier.padding(vertical = KotlifinTheme.dimensions.spacingMedium))
                    }
                } else {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = state.isAddressValid(),
                        onClick = { dispatch(Intent.Connect(state.address)) }) {
                        Text(text = "Connect")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddServerTopAppBar(
    onBackPressed: () -> Unit = {}
) {
    LargeTopAppBar(
        title = {
            Text(text = "Connect")
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = "back icon"
                )
            }
        }
    )
}

const val AddServerRoute = "add_server"

fun NavGraphBuilder.addServerScreen(navController: NavController) {
    composable(AddServerRoute) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(ServerListRoute)
        }
        val viewModel: ServerManagementViewModel = hiltViewModel(parentEntry)
        val state by viewModel.viewStates.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.viewEvents.collect { event ->
                when (event) {
                    is Event.NavigateToSignIn -> navController.navigateToSignIn()
                    is Event.NavigateToHome -> {}
                    is Event.NavigateToSelectUser -> {}
                }
            }
        }

        AddServerScreen(
            state = state,
            dispatch = viewModel::dispatch,
            onBackPressed = navController::navigateUp
        )
    }
}

fun NavController.navigateToAddServerScreen() {
    navigate(route = AddServerRoute)
}

@Preview
@Composable
private fun AddServerScreenPreview() {
    KotlifinTheme {
        AddServerScreen()
    }
}
