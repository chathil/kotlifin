package com.chathil.kotlifin.data.dto.extension

import com.chathil.kotlifin.data.model.server.JellyfinServer
import org.jellyfin.sdk.model.api.PublicSystemInfo

fun PublicSystemInfo.toJellyfinServer(publicAddress: String, userCount: Int = 0): JellyfinServer {
    return JellyfinServer(
        id ?: "",
        name = productName ?: "",
        publicAddress = publicAddress,
        localAddress = localAddress ?: "",
        version = version ?: "",
        productName = productName ?: "",
        operatingSystem = operatingSystem ?: "",
        users = emptyList()
    )
}
