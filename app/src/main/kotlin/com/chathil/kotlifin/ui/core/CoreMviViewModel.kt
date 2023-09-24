package com.chathil.kotlifin.ui.core

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@FlowPreview
abstract class CoreMviViewModel<Intent : Any, Action : Any, Result : Any, State : Any, Event : Any>(
    private val initialViewState: State,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), MviViewModel<Intent, State, Event> {

    private val _viewStates = MutableStateFlow(
        savedStateHandle.get<State>(SAVED_STATE) ?: initialViewState
    )
    override val viewStates: StateFlow<State> = _viewStates.asStateFlow()

    private val _viewEvents = MutableSharedFlow<Event>()
    override val viewEvents: SharedFlow<Event> = _viewEvents.asSharedFlow()

    private val userIntent = Channel<Intent>()

    protected abstract fun actionToResult(action: Action): Flow<Result>

    protected abstract fun intentToAction(intent: Intent): Action

    protected abstract fun reducer(state: State, result: Result): State

    init {
        userIntent.consume()
    }

    fun dispatch(intent: Intent) {
        viewModelScope.launch {
            Timber.tag(CoreMviViewModel::class.java.simpleName).d("dispatch: ${ intent::class.java.simpleName }")
            userIntent.send(intent)
        }
    }

    protected fun sendViewEvent(event: Event) {
        viewModelScope.launch {
            Timber.tag(CoreMviViewModel::class.java.simpleName).d("sendViewEvent: ${event::class.java.simpleName}")
            _viewEvents.emit(event)
        }
    }

    override fun setInitialState(state: State) {
        _viewStates.value = state
    }

    private fun Channel<Intent>.consume() {
        consumeAsFlow().map(::intentToAction)
            .map(::actionToResult).flattenMerge()
            .map { result -> reducer(_viewStates.value, result) }
            .onEach { state ->
                _viewStates.value = state
                Timber.tag(CoreMviViewModel::class.java.simpleName).d(state.toString())
            }
            .launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        userIntent.close()
        if (initialViewState is Parcelable) {
            savedStateHandle[SAVED_STATE] = _viewStates.value
        }
    }
}

private const val SAVED_STATE = "saved_state"
