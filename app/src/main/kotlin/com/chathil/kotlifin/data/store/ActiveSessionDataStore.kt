package com.chathil.kotlifin.data.store

import com.chathil.kotlifin.data.model.session.ActiveSession
import kotlinx.coroutines.flow.Flow

interface ActiveSessionDataStore {
    suspend fun changeSession(session: ActiveSession): Unit

    val activeSession: Flow<ActiveSession>

    fun clear(): Flow<Unit>
}
