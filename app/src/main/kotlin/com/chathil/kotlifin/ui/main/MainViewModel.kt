package com.chathil.kotlifin.ui.main

import androidx.lifecycle.SavedStateHandle
import com.chathil.kotlifin.ui.core.CoreMviViewModel
import com.chathil.kotlifin.ui.main.mvi.Action
import com.chathil.kotlifin.ui.main.mvi.Event
import com.chathil.kotlifin.ui.main.mvi.Intent
import com.chathil.kotlifin.ui.main.mvi.Result
import com.chathil.kotlifin.ui.main.mvi.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : CoreMviViewModel<Intent, Action, Result, State, Event>(State.Initial, savedStateHandle) {

    override fun actionToResult(action: Action): Flow<Result> {
        TODO("Not yet implemented")
    }

    override fun intentToAction(intent: Intent): Action {
        TODO("Not yet implemented")
    }

    override fun reducer(state: State, result: Result): State {
        TODO("Not yet implemented")
    }

}
