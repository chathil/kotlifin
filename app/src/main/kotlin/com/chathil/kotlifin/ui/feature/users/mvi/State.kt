package com.chathil.kotlifin.ui.feature.users.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.user.JellyfinUser

data class State(
    val isLoading: Boolean,
    val server: JellyfinServer,
    val users: List<JellyfinUser>,
    val error: Throwable?
) {
    companion object {
        val Initial = State(
            isLoading = false,
            server = JellyfinServer.Empty,
            users = emptyList(),
            error = null
        )
    }
}
