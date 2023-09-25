package com.chathil.kotlifin.ui.feature.server.signin

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.ui.feature.home.navigateToHomeScreen
import com.chathil.kotlifin.ui.feature.server.ServerManagementViewModel
import com.chathil.kotlifin.ui.feature.server.list.ServerListRoute
import com.chathil.kotlifin.ui.feature.server.mvi.Event
import com.chathil.kotlifin.ui.feature.server.mvi.Intent
import com.chathil.kotlifin.ui.feature.server.mvi.State
import com.chathil.kotlifin.ui.theme.KotlifinTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerSignInScreen(
    state: State = State.Initial,
    dispatch: (Intent) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    Scaffold(topBar = { SignInTopAppBar(onBackPressed) }) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(KotlifinTheme.dimensions.spacingMedium)
        ) {
            Text(
                text = "Sign In to ${state.newServer.name}",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingXS))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.username,
                onValueChange = { username -> dispatch(Intent.UpdateUsername(username)) },
                singleLine = true,
                placeholder = { Text("Username") }
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.pwd,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { pwd -> dispatch(Intent.UpdatePwd(pwd)) },
                singleLine = true,
                placeholder = { Text("Password") }
            )
            Spacer(modifier = Modifier.height(KotlifinTheme.dimensions.spacingSmall))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isCredentialValid(),
                onClick = { dispatch(Intent.SignIn(state.username, state.pwd, state.newServer)) }) {
                Text(text = "Sign In")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SignInTopAppBar(onBackPressed: () -> Unit = {}) {
    LargeTopAppBar(
        title = {
            Text(text = "Sign In")
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

const val SignInRoute = "signin"

fun NavGraphBuilder.signInScreen(navController: NavController) {
    composable(SignInRoute) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(ServerListRoute)
        }
        val viewModel: ServerManagementViewModel = hiltViewModel(parentEntry)
        val state by viewModel.viewStates.collectAsStateWithLifecycle()
        LaunchedEffect(Unit) {
            viewModel.viewEvents.collect { event ->
                if (event is Event.NavigateToHome) navController.navigateToHomeScreen()
            }
        }

        ServerSignInScreen(
            state = state,
            dispatch = viewModel::dispatch,
            onBackPressed = navController::navigateUp
        )
    }
}

fun NavController.navigateToSignIn() {
    navigate(route = SignInRoute)
}

@Preview
@Composable
fun ServerSignInScreenPreview() {
    KotlifinTheme {
        ServerSignInScreen(state = State.Initial.copy(newServer = JellyfinServer.Empty.copy(name = "BotFace")))
    }
}
