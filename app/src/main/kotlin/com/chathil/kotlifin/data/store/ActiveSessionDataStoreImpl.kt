package com.chathil.kotlifin.data.store

import androidx.datastore.core.DataStore
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.model.session.ActiveSessionProto
import com.chathil.kotlifin.data.model.session.toActiveSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ActiveSessionDataStoreImpl(
    private val activeSessionSerializer: DataStore<ActiveSessionProto>
) : ActiveSessionDataStore {

    override suspend fun changeSession(session: ActiveSession) {
        activeSessionSerializer.updateData {
                ActiveSessionProto(
                    user_id = session.userId,
                    username = session.username,
                    server_id = session.serverId,
                    server_public_address = session.serverPublicAddress,
                    server_local_address = session.serverLocalAddress,
                    server_name = session.serverName,
                    access_token = session.accessToken,
                    device_uuid = session.deviceUuid
                )
            }
    }

    override val activeSession: Flow<ActiveSession>
        get() = activeSessionSerializer.data.map(ActiveSessionProto::toActiveSession)

    override fun clear(): Flow<Unit> {
        return flow {
            activeSessionSerializer.updateData {
                ActiveSessionProto()
            }
        }
    }
}
