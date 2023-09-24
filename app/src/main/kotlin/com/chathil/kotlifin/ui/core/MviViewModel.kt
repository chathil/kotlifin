package com.chathil.kotlifin.ui.core

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MviViewModel<Intent : Any, State : Any, Event : Any> {

    val viewStates: StateFlow<State>

    val viewEvents: SharedFlow<Event>

    fun setInitialState(state: State)
}
