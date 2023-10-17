package com.chathil.kotlifin.ui.feature.server.mvi

import com.chathil.kotlifin.data.model.server.JellyfinServer
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.model.user.JellyfinUser
import com.chathil.kotlifin.data.vo.Resource

sealed interface Result {
    data class ConnectResult(val data: Resource<JellyfinServer>): Result
    data class SelectedServer(val server: JellyfinServer): Result
    data class SelectedUser(val user: JellyfinUser): Result
    data class LoadServersResult(val data: Resource<List<JellyfinServer>>): Result
    data class UpdateAddress(val address: String): Result
    data class UpdateUsername(val username: String): Result
    data class UpdatePwd(val pwd: String): Result
    data class SignInResult(val data: Resource<JellyfinUser>): Result
    data class RemoveServer(val data: Resource<Unit>) : Result
}
