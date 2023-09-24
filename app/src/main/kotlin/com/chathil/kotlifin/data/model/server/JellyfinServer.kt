package com.chathil.kotlifin.data.model.server

import com.chathil.kotlifin.data.model.user.JellyfinUser
import com.chathil.kotlifin.data.room.entity.JellyfinServerEntity

data class JellyfinServer(
    val id: String,
    val name: String,
    val publicAddress: String,
    val localAddress: String,
    val version: String,
    val productName: String,
    val operatingSystem: String,
    val users: List<JellyfinUser>
) {
    companion object {
        val Empty = JellyfinServer(
            id = "",
            name = "",
            publicAddress = "",
            localAddress = "",
            version = "",
            productName = "",
            operatingSystem = "",
            users = emptyList()
        )
    }
}

fun JellyfinServer.toJellyfinServerEntity(): JellyfinServerEntity {
    return JellyfinServerEntity(
        id = id,
        address = publicAddress,
        localAddress = localAddress,
        name = name,
        version = version,
        productName = productName,
        operatingSystem = operatingSystem
    )
}
