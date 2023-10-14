package com.chathil.kotlifin.ui.feature.server

import androidx.lifecycle.SavedStateHandle
import com.chathil.kotlifin.data.repository.server.ServerRepository
import com.chathil.kotlifin.data.repository.user.UserRepository
import com.chathil.kotlifin.data.vo.Resource
import com.chathil.kotlifin.ui.core.CoreMviViewModel
import com.chathil.kotlifin.ui.feature.server.mvi.Action
import com.chathil.kotlifin.ui.feature.server.mvi.Event
import com.chathil.kotlifin.ui.feature.server.mvi.Intent
import com.chathil.kotlifin.ui.feature.server.mvi.Result
import com.chathil.kotlifin.ui.feature.server.mvi.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ServerManagementViewModel @Inject constructor(
    private val serverRepository: ServerRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : CoreMviViewModel<Intent, Action, Result, State, Event>(State.Initial, savedStateHandle) {

    init {
        dispatch(Intent.LoadServers)
    }

    override fun actionToResult(action: Action): Flow<Result> = when(action) {
        is Action.Connect -> serverRepository.connectToServer(action.address)
            .map(Result::ConnectResult)

        is Action.SelectedServer -> flowOf(Result.SelectedServer(action.server))
        is Action.SelectedUser -> flowOf(Result.SelectedUser(action.user))
        is Action.UpdateAddress -> flowOf(Result.UpdateAddress(action.address))
        is Action.UpdatePwd -> flowOf(Result.UpdatePwd(action.pwd))
        is Action.UpdateUsername -> flowOf(Result.UpdateUsername(action.username))
        is Action.LoadServers -> serverRepository.loadServers().map(Result::LoadServersResult)
        is Action.SignIn -> userRepository.signIn(action.username, action.pwd, action.server).map(Result::SignInResult)
    }

    override fun intentToAction(intent: Intent): Action = when(intent) {
        is Intent.Connect -> Action.Connect(intent.address)
        is Intent.SelectedServer -> Action.SelectedServer(intent.server)
        is Intent.SelectedUser -> Action.SelectedUser(intent.user)
        is Intent.UpdateAddress -> Action.UpdateAddress(intent.address)
        is Intent.UpdatePwd -> Action.UpdatePwd(intent.pwd)
        is Intent.UpdateUsername -> Action.UpdateUsername(intent.username)
        is Intent.LoadServers -> Action.LoadServers
        is Intent.SignIn -> Action.SignIn(intent.username, intent.pwd, intent.server)
    }

    override fun reducer(state: State, result: Result): State = when(result) {
        is Result.ConnectResult -> when(result.data) {
            is Resource.Loading -> state.copy(isLoading = true, error = null)
            is Resource.Success -> state.copy(isLoading = false, newServer = result.data.data).also {
                sendViewEvent(Event.NavigateToSignIn)
            }
            is Resource.Error -> state.copy(isLoading = false, error = result.data.error)
        }

        is Result.SelectedServer -> state.copy(newServer = result.server).also {
            sendViewEvent(Event.NavigateToSelectUser(result.server))
        }
        is Result.SelectedUser -> state.copy(selectedUser = result.user)
        is Result.UpdateAddress -> state.copy(address = result.address)
        is Result.UpdatePwd -> state.copy(pwd = result.pwd)
        is Result.UpdateUsername -> state.copy(username = result.username)
        is Result.LoadServersResult -> when(result.data) {
            is Resource.Loading -> state.copy(isLoading = true, error = null)
            is Resource.Success -> state.copy(isLoading = false, savedServers = result.data.data)
            is Resource.Error -> state.copy(isLoading = false, error = result.data.error)
        }

        is Result.SignInResult -> when(result.data) {
            is Resource.Loading -> state.copy(isLoading = true, error = null)
            is Resource.Success -> state.copy(isLoading = false).also {
                sendViewEvent(Event.NavigateToHome)
            }
            is Resource.Error -> state.copy(isLoading = false, error = result.data.error)
        }
    }
}
