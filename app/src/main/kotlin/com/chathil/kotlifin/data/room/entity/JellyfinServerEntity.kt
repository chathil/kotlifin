package com.chathil.kotlifin.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.user.JellyfinUser

@Entity("jellyfin_server")
data class JellyfinServerEntity(
    @PrimaryKey val id: String,
    val address: String,
    @ColumnInfo("local_address") val localAddress: String,
    @ColumnInfo("server_name") val name: String,
    @ColumnInfo("version") val version: String,
    @ColumnInfo("product_name") val productName: String,
    @ColumnInfo("operating_system") val operatingSystem: String
)

fun JellyfinServerEntity.toJellyfinServer(users: List<JellyfinUser>): JellyfinServer {
    return JellyfinServer(
        id = id,
        name = productName,
        publicAddress = address,
        localAddress = localAddress,
        version = version,
        productName = productName,
        operatingSystem = operatingSystem,
        users = users
    )
}
