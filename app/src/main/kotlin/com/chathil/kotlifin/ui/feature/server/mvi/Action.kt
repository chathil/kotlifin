package com.chathil.kotlifin.ui.feature.server.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.model.user.JellyfinUser

sealed interface Action {
    data class Connect(val address: String): Action
    data class SelectedServer(val server: JellyfinServer): Action
    data class SelectedUser(val user: JellyfinUser): Action
    object LoadServers: Action
    data class UpdateAddress(val address: String): Action
    data class UpdateUsername(val username: String): Action
    data class UpdatePwd(val pwd: String): Action
    data class SignIn(val username: String, val pwd: String, val server: JellyfinServer): Action
}
