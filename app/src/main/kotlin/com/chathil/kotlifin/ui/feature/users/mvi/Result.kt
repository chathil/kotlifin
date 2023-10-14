package com.chathil.kotlifin.ui.feature.users.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.user.JellyfinUser
import com.chathil.kotlifin.data.vo.Resource

sealed interface Result {
    data class LoadUsers(val data: Resource<List<JellyfinUser>>) : Result
    data class LoadServerDetail(val data: Resource<JellyfinServer>) : Result
    object SwitchSession : Result
    data class RemoveUserResult(val data: Resource<Unit>) : Result
}
