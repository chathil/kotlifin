package com.chathil.kotlifin.data.model.user

import com.chathil.kotlifin.data.room.entity.JellyfinUserEntity

data class JellyfinUser(
    val id: String,
    val name: String,
    val serverId: String,
    val primaryImageTag: String,
    val hasPassword: Boolean,
    val hasConfiguredPassword: Boolean,
    val hasConfiguredEasyPassword: Boolean,
    val enableAutoLogin: Boolean,
    val accessToken: String,
    val deviceUuid: String
) {
    companion object {
        val Empty = JellyfinUser(
            id = "",
            name = "",
            serverId = "",
            primaryImageTag = "",
            hasPassword = false,
            hasConfiguredPassword = false,
            hasConfiguredEasyPassword = false,
            enableAutoLogin = false,
            accessToken = "",
            deviceUuid = ""
        )
    }
}

fun JellyfinUser.toJellyfinUserEntity(): JellyfinUserEntity {
    return JellyfinUserEntity(
        id = id,
        name = name,
        serverId = serverId,
        primaryImageTag = primaryImageTag,
        hasPassword = hasPassword,
        hasConfiguredPassword = hasConfiguredPassword,
        hasConfiguredEasyPassword = hasConfiguredEasyPassword,
        enableAutoLogin = enableAutoLogin,
        accessToken = accessToken,
        deviceInfoPostFix = deviceUuid
    )
}
