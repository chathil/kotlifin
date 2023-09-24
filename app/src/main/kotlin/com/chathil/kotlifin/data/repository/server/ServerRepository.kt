package com.chathil.kotlifin.data.repository.server

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ServerRepository {
    fun addServer(server: JellyfinServer): Flow<Resource<Unit>>

    fun removeServer(serverId: String): Flow<Resource<Unit>>

    fun loadServers(): Flow<Resource<List<JellyfinServer>>>

    fun loadServer(serverId: String): Flow<Resource<JellyfinServer>>

    fun connectToServer(serverAddress: String): Flow<Resource<JellyfinServer>>
}