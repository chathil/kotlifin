package com.chathil.kotlifin.data.model.session

data class ActiveSession(
    val userId: String,
    val username: String,
    val serverId: String,
    val serverPublicAddress: String,
    val serverLocalAddress: String,
    val serverName: String,
    val accessToken: String,
    val deviceUuid: String
) {
    companion object {
        val Empty = ActiveSession(
            userId = "",
            username = "",
            serverId = "",
            serverPublicAddress = "",
            serverLocalAddress = "",
            serverName = "",
            accessToken = "",
            deviceUuid = ""
        )
    }
}
