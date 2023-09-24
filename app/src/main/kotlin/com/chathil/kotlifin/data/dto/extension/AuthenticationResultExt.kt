package com.chathil.kotlifin.data.dto.extension

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.session.ActiveSession
import org.jellyfin.sdk.model.api.AuthenticationResult

fun AuthenticationResult.toActiveSession(
    server: JellyfinServer
): ActiveSession {
    return ActiveSession(
        userId = user?.id.toString(),
        username = user?.name ?: "",
        serverId = serverId ?: "",
        serverPublicAddress = server.publicAddress,
        serverLocalAddress = server.localAddress,
        serverName = server.name,
        accessToken = accessToken ?: ""
    )
}
