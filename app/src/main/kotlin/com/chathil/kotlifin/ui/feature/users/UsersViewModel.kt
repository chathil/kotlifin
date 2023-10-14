package com.chathil.kotlifin.ui.feature.users

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.repository.server.ServerRepository
import com.chathil.kotlifin.data.repository.user.UserRepository
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.data.vo.Resource
import com.chathil.kotlifin.ui.core.CoreMviViewModel
import com.chathil.kotlifin.ui.feature.users.mvi.Action
import com.chathil.kotlifin.ui.feature.users.mvi.Event
import com.chathil.kotlifin.ui.feature.users.mvi.Intent
import com.chathil.kotlifin.ui.feature.users.mvi.Result
import com.chathil.kotlifin.ui.feature.users.mvi.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val serverRepository: ServerRepository,
    private val activeSession: ActiveSessionDataStore,
    savedStateHandle: SavedStateHandle
) : CoreMviViewModel<Intent, Action, Result, State, Event>(State.Initial, savedStateHandle) {

    init {
        savedStateHandle.get<String>(UsersRouteServerId)?.takeIf(String::isNotBlank)?.let { id ->
            dispatch(Intent.LoadUsers(id))
            dispatch(Intent.LoadServerDetail(id))
        }
    }

    override fun actionToResult(action: Action): Flow<Result> = when (action) {
        is Action.LoadUsers -> userRepository.loadUsers(action.serverId).map(Result::LoadUsers)
        is Action.LoadServerDetail -> serverRepository.loadServer(action.serverId)
            .map(Result::LoadServerDetail)

        is Action.SwitchSession -> flowOf(Result.SwitchSession).map { result ->
            activeSession.changeSession(
                ActiveSession(
                    action.user.id,
                    action.user.name,
                    action.server.id,
                    action.server.publicAddress,
                    action.server.localAddress,
                    action.server.name,
                    action.user.accessToken
                )
            )
            result
        }

        is Action.RemoveUser -> userRepository.removeUser(action.user.id)
            .map(Result::RemoveUserResult)
    }

    override fun intentToAction(intent: Intent): Action = when (intent) {
        is Intent.LoadUsers -> Action.LoadUsers(intent.serverId)
        is Intent.LoadServerDetail -> Action.LoadServerDetail(intent.serverId)
        is Intent.SwitchSession -> Action.SwitchSession(intent.user, intent.server)
        is Intent.RemoveUser -> Action.RemoveUser(intent.user)
    }

    override fun reducer(state: State, result: Result): State = when (result) {
        is Result.LoadUsers -> when (result.data) {
            is Resource.Loading -> state.copy(isLoading = true)
            is Resource.Success -> state.copy(users = result.data.data)
            is Resource.Error -> state.copy(error = result.data.error)
        }

        is Result.LoadServerDetail -> when (result.data) {
            is Resource.Loading -> state.copy(isLoading = true)
            is Resource.Success -> state.copy(server = result.data.data)
            is Resource.Error -> state.copy(error = result.data.error)
        }

        is Result.SwitchSession -> state.also {
            sendViewEvent(Event.NavigateToHome)
        }
        is Result.RemoveUserResult -> when (result.data) {
            is Resource.Loading -> state.copy(isLoading = true)
            is Resource.Success -> state.copy(isLoading = false).also {
                dispatch(Intent.LoadUsers(state.server.id))
            }
            is Resource.Error -> state.copy(error = result.data.error)
        }
    }
}
