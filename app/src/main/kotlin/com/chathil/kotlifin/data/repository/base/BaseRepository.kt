package com.chathil.kotlifin.data.repository.base

import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.HttpClientOptions
import org.jellyfin.sdk.api.client.exception.ApiClientException
import org.jellyfin.sdk.model.serializer.toUUID

abstract class BaseRepository(
    private val jellyfin: Jellyfin,
    private val activeSession: ActiveSessionDataStore
) {

    // TODO: Try to access serverLocalAddress first
    fun api(serverAddress: String = ""): Flow<ApiClient> {
        return activeSession.activeSession
            .take(1)
            .map { session ->
                jellyfin.createApi(
                    serverAddress.ifBlank { session.serverPublicAddress },
                    session.accessToken.ifBlank { null },
                    session.userId.ifBlank { null }?.toUUID(),
                )
            }
    }
}
