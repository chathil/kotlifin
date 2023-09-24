package com.chathil.kotlifin.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chathil.kotlifin.data.model.user.JellyfinUser

@Entity("jellyfin_user")
data class JellyfinUserEntity(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo("server_id") val serverId: String,
    @ColumnInfo("primary_image_tag") val primaryImageTag: String,
    @ColumnInfo("has_password") val hasPassword: Boolean,
    @ColumnInfo("has_configured_password") val hasConfiguredPassword: Boolean,
    @ColumnInfo("has_configured_easy_password") val hasConfiguredEasyPassword: Boolean,
    @ColumnInfo("enable_auto_login") val enableAutoLogin: Boolean,
    @ColumnInfo("access_token") val accessToken: String
)

fun JellyfinUserEntity.toJellyfinUser(): JellyfinUser {
    return JellyfinUser(
        id = id,
        name = name,
        serverId = serverId,
        primaryImageTag = primaryImageTag,
        hasPassword = hasPassword,
        hasConfiguredPassword = hasConfiguredPassword,
        hasConfiguredEasyPassword = hasConfiguredEasyPassword,
        enableAutoLogin = enableAutoLogin,
        accessToken = accessToken
    )
}

fun List<JellyfinUserEntity>.toJellyfinUsers(): List<JellyfinUser> =
    map(JellyfinUserEntity::toJellyfinUser)
