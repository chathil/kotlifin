package com.chathil.kotlifin.ui.feature.server.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer

sealed interface Event {
    object NavigateToSignIn : Event
    object NavigateToHome : Event
    data class NavigateToSelectUser(val server: JellyfinServer) : Event
}
