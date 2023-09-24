package com.chathil.kotlifin.data.repository.server

import com.chathil.kotlifin.data.dto.extension.toJellyfinServer
import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.server.toJellyfinServerEntity
import com.chathil.kotlifin.data.model.user.JellyfinUser
import com.chathil.kotlifin.data.model.user.toJellyfinUserEntity
import com.chathil.kotlifin.data.repository.base.BaseRepository
import com.chathil.kotlifin.data.room.KotlifinDb
import com.chathil.kotlifin.data.room.dao.JellyfinServerDao
import com.chathil.kotlifin.data.room.entity.JellyfinServerEntity
import com.chathil.kotlifin.data.room.entity.JellyfinUserEntity
import com.chathil.kotlifin.data.room.entity.toJellyfinServer
import com.chathil.kotlifin.data.room.entity.toJellyfinUser
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.data.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.api.client.Response
import org.jellyfin.sdk.api.client.extensions.systemApi
import org.jellyfin.sdk.api.operations.SystemApi
import org.jellyfin.sdk.model.api.PublicSystemInfo
import org.jellyfin.sdk.model.serializer.toUUID
import javax.inject.Inject

class ServerRepositoryImpl @Inject constructor(
    jellyfin: Jellyfin,
    activeSession: ActiveSessionDataStore,
    private val localDataSource: KotlifinDb
) : ServerRepository, BaseRepository(jellyfin, activeSession) {

    override fun addServer(server: JellyfinServer): Flow<Resource<Unit>> {
        return flow<Resource<Unit>> {
            localDataSource.serverDao().insert(server.toJellyfinServerEntity())
            localDataSource.userDao().insert(server.users.first().toJellyfinUserEntity())
        }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun removeServer(serverId: String): Flow<Resource<Unit>> {
        return flow<Resource<Unit>> {
            localDataSource.serverDao().deleteServerById(serverId)
            localDataSource.userDao().deleteUserByServerId(serverId)
        }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun loadServers(): Flow<Resource<List<JellyfinServer>>> {
        return localDataSource.serverDao().getAllServers().flatMapLatest { servers ->
            localDataSource.userDao().getAllUsers().map { users ->
                servers to users.map(JellyfinUserEntity::toJellyfinUser)
                    .groupBy(JellyfinUser::serverId)
            }
        }.map { (servers, users) ->
            servers.map { server ->
                server.toJellyfinServer(users = users[server.id] ?: emptyList())
            }
        }.map<List<JellyfinServer>, Resource<List<JellyfinServer>>> { servers ->
            Resource.Success(servers)
        }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
    }

    override fun loadServer(serverId: String): Flow<Resource<JellyfinServer>> {
        return localDataSource.serverDao().loadServerById(serverId)
            .map<JellyfinServerEntity, Resource<JellyfinServer>> { server ->
                Resource.Success(server.toJellyfinServer(emptyList()))
            }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
    }

    override fun connectToServer(serverAddress: String): Flow<Resource<JellyfinServer>> {
        return api(serverAddress).map { client -> client.systemApi }
            .map(SystemApi::getPublicSystemInfo)
            .map<Response<PublicSystemInfo>, Resource<JellyfinServer>> { sysInfoResponse ->
                Resource.Success(sysInfoResponse.content.toJellyfinServer(serverAddress))
            }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
    }
}
