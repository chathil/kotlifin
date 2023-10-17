package com.chathil.kotlifin.ui.feature.server.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.model.user.JellyfinUser

sealed interface Intent {
    data class Connect(val address: String): Intent
    data class SelectedServer(val server: JellyfinServer): Intent
    data class SelectedUser(val user: JellyfinUser): Intent
    object LoadServers: Intent
    data class UpdateAddress(val address: String): Intent
    data class UpdateUsername(val username: String): Intent
    data class UpdatePwd(val pwd: String): Intent
    data class SignIn(val username: String, val pwd: String, val server: JellyfinServer): Intent
    data class RemoveServer(val serverId: String) : Intent
}
