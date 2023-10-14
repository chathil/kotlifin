package com.chathil.kotlifin.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.ui.feature.home.HomeRoute
import com.chathil.kotlifin.ui.feature.home.homeScreen
import com.chathil.kotlifin.ui.feature.home.navigateToHomeScreen
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
        setContent {
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
                            onSelectUser = { serverId -> navController.navigateToUsersScreen(serverId) }
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
                        goToSettings = { navController.navigateToSettingsScreen() }
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
