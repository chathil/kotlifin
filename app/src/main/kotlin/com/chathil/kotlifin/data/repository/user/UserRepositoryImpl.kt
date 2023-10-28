package com.chathil.kotlifin.data.repository.user

import com.chathil.kotlifin.data.cache.Constants
import com.chathil.kotlifin.data.cache.InMemoryCache
import com.chathil.kotlifin.data.cache.NewSessionInMemoryCache
import com.chathil.kotlifin.data.dto.extension.toActiveSession
import com.chathil.kotlifin.data.dto.extension.toJellyfinUser
import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.server.toJellyfinServerEntity
import com.chathil.kotlifin.data.model.user.JellyfinUser
import com.chathil.kotlifin.data.model.user.toJellyfinUserEntity
import com.chathil.kotlifin.data.repository.base.BaseRepository
import com.chathil.kotlifin.data.room.KotlifinDb
import com.chathil.kotlifin.data.room.entity.JellyfinUserEntity
import com.chathil.kotlifin.data.room.entity.toJellyfinUsers
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.data.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.api.client.Response
import org.jellyfin.sdk.api.client.exception.ApiClientException
import org.jellyfin.sdk.api.client.extensions.authenticateUserByName
import org.jellyfin.sdk.api.client.extensions.userApi
import org.jellyfin.sdk.model.UUID
import org.jellyfin.sdk.model.api.AuthenticationResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    jellyfin: Jellyfin,
    private val activeSession: ActiveSessionDataStore,
    private val localDataSource: KotlifinDb,
    private val inMemoryCache: InMemoryCache<String, String>,
) : UserRepository, BaseRepository(jellyfin, activeSession) {

    override fun signIn(
        username: String,
        pwd: String,
        server: JellyfinServer
    ): Flow<Resource<JellyfinUser>> {
        val deviceUuid = inMemoryCache.fetch(Constants.NEW_USER_DEVICE_POST_FIX)
            ?: inMemoryCache.store(Constants.NEW_USER_DEVICE_POST_FIX, UUID.randomUUID().toString())

        return api(server.publicAddress, deviceUuid).map { api ->
            api.userApi.authenticateUserByName(username, pwd)
        }
            .map<Response<AuthenticationResult>, Resource<JellyfinUser>> { response ->
                val user = response.content.user ?: throw ApiClientException("Invalid credential")
                activeSession.changeSession(response.content.toActiveSession(server, deviceUuid))
                localDataSource.serverDao().insert(server.toJellyfinServerEntity())
                localDataSource.userDao().insert(
                    user.toJellyfinUser(response.content.accessToken ?: "", deviceUuid).toJellyfinUserEntity()
                )
                Resource.Success(user.toJellyfinUser(response.content.accessToken ?: "", deviceUuid))
            }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
    }

    override fun removeUser(userId: String, serverId: String): Flow<Resource<Unit>> {
        return flow<Resource<Unit>> {
            localDataSource.userDao().deleteUserById(userId)
        }.flatMapLatest {
            localDataSource.userDao().loadUsersByServerId(serverId)
        }.map<List<JellyfinUserEntity>, Resource<Unit>> { users ->
            if (users.isEmpty()) {
                localDataSource.serverDao().deleteServerById(serverId)
            }
            Resource.Success(Unit)
        }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
    }

    override fun loadUsers(serverId: String): Flow<Resource<List<JellyfinUser>>> {
        return localDataSource.userDao().loadUsersByServerId(serverId)
            .map<List<JellyfinUserEntity>, Resource<List<JellyfinUser>>> { users ->
                Resource.Success(users.toJellyfinUsers())
            }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
    }
}
