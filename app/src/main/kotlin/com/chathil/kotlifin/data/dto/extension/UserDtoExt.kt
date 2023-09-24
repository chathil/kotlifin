package com.chathil.kotlifin.data.dto.extension

import com.chathil.kotlifin.data.model.user.JellyfinUser
import org.jellyfin.sdk.model.api.UserDto

fun UserDto.toJellyfinUser(accessToken: String): JellyfinUser {
    return JellyfinUser(
        id = id.toString(),
        name = name ?: "",
        serverId = serverId ?: "",
        primaryImageTag = primaryImageTag ?: "",
        hasPassword = hasPassword,
        hasConfiguredPassword = hasConfiguredPassword,
        hasConfiguredEasyPassword = hasConfiguredEasyPassword,
        enableAutoLogin = enableAutoLogin ?: false,
        accessToken = accessToken
    )
}
