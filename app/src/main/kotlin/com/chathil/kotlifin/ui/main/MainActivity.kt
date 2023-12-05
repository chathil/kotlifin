package com.chathil.kotlifin.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.ui.feature.home.HomeRoute
import com.chathil.kotlifin.ui.feature.home.homeScreen
import com.chathil.kotlifin.ui.feature.home.navigateToHomeScreen
import com.chathil.kotlifin.ui.feature.list.listScreen
import com.chathil.kotlifin.ui.feature.list.navigateToListScreen
import com.chathil.kotlifin.ui.feature.server.ServerManagementRoute
import com.chathil.kotlifin.ui.feature.server.ServerManagementViewModel
import com.chathil.kotlifin.ui.feature.server.add.addServerScreen
import com.chathil.kotlifin.ui.feature.server.add.navigateToAddServerScreen
import com.chathil.kotlifin.ui.feature.server.list.ServerListRoute
import com.chathil.kotlifin.ui.feature.server.list.serverListScreen
import com.chathil.kotlifin.ui.feature.server.navigateToServerManagerScreens
import com.chathil.kotlifin.ui.feature.server.signin.navigateToSignIn
import com.chathil.kotlifin.ui.feature.server.signin.signInScreen
import com.chathil.kotlifin.ui.feature.settings.navigateToSettingsScreen
import com.chathil.kotlifin.ui.feature.settings.settingsScreen
import com.chathil.kotlifin.ui.feature.users.navigateToUsersScreen
import com.chathil.kotlifin.ui.feature.users.usersScreen
import com.chathil.kotlifin.ui.theme.KotlifinTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var activeSessionDataStore: ActiveSessionDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()

        setContent {
            val darkTheme = isSystemInDarkTheme()
            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
            }

            val activeSession by activeSessionDataStore.activeSession.collectAsStateWithLifecycle(
                initialValue = ActiveSession.Empty
            )
            val navController = rememberNavController()
            KotlifinTheme {
                val startDestination =
                    if (activeSession == ActiveSession.Empty) ServerManagementRoute else HomeRoute
                NavHost(navController = navController, startDestination = startDestination) {

                    navigation(startDestination = ServerListRoute, route = "management") {
                        serverListScreen(
                            onAddServer = { navController.navigateToAddServerScreen() },
                            onSelectUser = { serverId ->
                                navController.navigateToUsersScreen(
                                    serverId
                                )
                            }
                        )
                        addServerScreen(
                            navController = navController
                        )
                        usersScreen(
                            onBackPressed = { navController.navigateUp() },
                            goToHome = {
                                navController.navigateToHomeScreen()
                            },
                            goToSignIn = {
                                navController.navigateToSignIn()
                            }
                        )
                        signInScreen(
                            navController = navController
                        )
                    }

                    homeScreen(
                        goToSettings = { navController.navigateToSettingsScreen() },
                        goToListScreen = { navController.navigateToListScreen(it) }
                    )

                    listScreen(
                        onBackPressed = { navController.navigateUp() }
                    )

                    settingsScreen(
                        manageServer = { navController.navigateToServerManagerScreens() },
                        onBackPressed = { navController.navigateUp() }
                    )
                }
            }
        }
    }
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)
