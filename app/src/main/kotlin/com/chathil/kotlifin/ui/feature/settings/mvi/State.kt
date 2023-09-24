package com.chathil.kotlifin.ui.feature.settings.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.session.ActiveSession

data class State(
    val isLoading: Boolean,
    val activeSession: ActiveSession,
    val currentServer: JellyfinServer,
    val error: Throwable?
) {
    companion object {
        val Initial = State(
            isLoading = false,
            activeSession = ActiveSession.Empty,
            currentServer = JellyfinServer.Empty,
            error = null
        )
    }
}
