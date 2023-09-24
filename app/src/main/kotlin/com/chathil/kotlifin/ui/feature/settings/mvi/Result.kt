package com.chathil.kotlifin.ui.feature.settings.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.vo.Resource

sealed interface Result {
    data class SaveActiveSession(
        val session: ActiveSession,
        val server: Resource<JellyfinServer>
    ) : Result
}
