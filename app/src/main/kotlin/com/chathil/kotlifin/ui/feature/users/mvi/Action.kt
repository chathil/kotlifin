package com.chathil.kotlifin.ui.feature.users.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.user.JellyfinUser

sealed interface Action {
    data class LoadUsers(val serverId: String) : Action
    data class LoadServerDetail(val serverId: String) : Action
    data class SwitchSession(val user: JellyfinUser, val server: JellyfinServer) : Action
    data class RemoveUser(val user: JellyfinUser, val server: JellyfinServer) : Action
}
