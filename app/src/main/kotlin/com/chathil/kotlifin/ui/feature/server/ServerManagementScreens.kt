package com.chathil.kotlifin.ui.feature.server

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chathil.kotlifin.ui.feature.home.homeScreen
import com.chathil.kotlifin.ui.feature.home.navigateToHomeScreen
import com.chathil.kotlifin.ui.feature.server.add.addServerScreen
import com.chathil.kotlifin.ui.feature.server.add.navigateToAddServerScreen
import com.chathil.kotlifin.ui.feature.server.list.ServerListRoute
import com.chathil.kotlifin.ui.feature.server.list.serverListScreen
import com.chathil.kotlifin.ui.feature.server.signin.navigateToSignIn
import com.chathil.kotlifin.ui.feature.server.signin.signInScreen
import com.chathil.kotlifin.ui.feature.settings.navigateToSettingsScreen
import com.chathil.kotlifin.ui.feature.users.navigateToUsersScreen
import com.chathil.kotlifin.ui.feature.users.usersScreen
import com.chathil.kotlifin.ui.theme.KotlifinTheme

const val ServerManagementRoute = "management"


fun NavController.navigateToServerManagerScreens() = navigate(route = ServerManagementRoute)