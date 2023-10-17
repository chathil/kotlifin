package com.chathil.kotlifin.ui.feature.users.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.user.JellyfinUser

sealed interface Intent {
    data class LoadUsers(val serverId: String) : Intent
    data class LoadServerDetail(val serverId: String) : Intent
    data class SwitchSession(val user: JellyfinUser, val server: JellyfinServer) : Intent
    data class RemoveUser(val user: JellyfinUser, val server: JellyfinServer) : Intent
}
