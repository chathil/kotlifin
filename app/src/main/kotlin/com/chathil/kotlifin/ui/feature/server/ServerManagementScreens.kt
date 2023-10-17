package com.chathil.kotlifin.ui.feature.server

import androidx.navigation.NavController

const val ServerManagementRoute = "management"

// TODO: Remove this nonsense
fun NavController.navigateToServerManagerScreens() = navigate(route = ServerManagementRoute)