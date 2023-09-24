package com.chathil.kotlifin.ui.feature.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chathil.kotlifin.data.repository.server.ServerRepository
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.data.vo.Resource
import com.chathil.kotlifin.ui.core.CoreMviViewModel
import com.chathil.kotlifin.ui.feature.settings.mvi.Action
import com.chathil.kotlifin.ui.feature.settings.mvi.Event
import com.chathil.kotlifin.ui.feature.settings.mvi.Intent
import com.chathil.kotlifin.ui.feature.settings.mvi.Result
import com.chathil.kotlifin.ui.feature.settings.mvi.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val activeSession: ActiveSessionDataStore,
    private val serverRepository: ServerRepository,
    savedStateHandle: SavedStateHandle,
) : CoreMviViewModel<Intent, Action, Result, State, Event>(State.Initial, savedStateHandle) {

    init {
        activeSession.activeSession
            .map(Intent::SaveActiveSession)
            .map(::dispatch)
            .launchIn(viewModelScope)
    }

    override fun actionToResult(action: Action): Flow<Result> = when (action) {
        is Action.SaveActiveSession -> serverRepository.loadServer(action.session.serverId)
            .map { response -> Result.SaveActiveSession(action.session, response) }
    }

    override fun intentToAction(intent: Intent): Action = when (intent) {
        is Intent.SaveActiveSession -> Action.SaveActiveSession(intent.session)
    }

    override fun reducer(state: State, result: Result): State = when (result) {
        is Result.SaveActiveSession -> when(result.server) {
            is Resource.Loading -> state.copy(isLoading = true)
            is Resource.Success -> state.copy(activeSession = result.session, currentServer = result.server.data)
            is Resource.Error -> state.copy(error = result.server.error)
        }
    }
}
