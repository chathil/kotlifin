package com.chathil.kotlifin.data.repository.user

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.user.JellyfinUser
import com.chathil.kotlifin.data.vo.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun signIn(username: String, pwd: String, server: JellyfinServer): Flow<Resource<JellyfinUser>>
    fun removeUser(id: String): Flow<Resource<Unit>>
    fun loadUsers(serverId: String): Flow<Resource<List<JellyfinUser>>>
}
