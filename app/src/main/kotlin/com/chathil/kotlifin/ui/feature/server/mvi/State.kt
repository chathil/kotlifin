package com.chathil.kotlifin.ui.feature.server.mvi

import android.webkit.URLUtil
import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.user.JellyfinUser

data class State(
    val isLoading: Boolean,
    val error: Throwable?,
    val newServer: JellyfinServer,
    val savedServers: List<JellyfinServer>,
    val address: String,
    val username: String,
    val pwd: String,
    val selectedUser: JellyfinUser
) {
    companion object {
        val Initial = State(
            isLoading = false,
            error = null,
            newServer = JellyfinServer.Empty,
            savedServers = emptyList(),
            address = "http://",
            username = "",
            pwd = "",
            selectedUser = JellyfinUser.Empty
        )
    }

    fun isAddressValid(): Boolean {
        return URLUtil.isValidUrl(address)
    }

    fun isCredentialValid(): Boolean {
        return username.isNotEmpty()
    }
}
