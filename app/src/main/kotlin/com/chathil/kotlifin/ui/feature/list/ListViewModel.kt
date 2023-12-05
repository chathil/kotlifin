package com.chathil.kotlifin.ui.feature.list

import androidx.lifecycle.SavedStateHandle
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.dto.request.movie.MediaRequest
import com.chathil.kotlifin.data.repository.media.MediaRepository
import com.chathil.kotlifin.ui.core.CoreMviViewModel
import com.chathil.kotlifin.ui.feature.list.mvi.Action
import com.chathil.kotlifin.ui.feature.list.mvi.Event
import com.chathil.kotlifin.ui.feature.list.mvi.Intent
import com.chathil.kotlifin.ui.feature.list.mvi.Result
import com.chathil.kotlifin.ui.feature.list.mvi.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val mediaRepositoryImpl: MediaRepository,
    savedStateHandle: SavedStateHandle
) : CoreMviViewModel<Intent, Action, Result, State, Event>(State.Initial, savedStateHandle) {

    companion object {
        const val LOAD_LIMIT = 15
        const val PREFETCH_DISTANCE = 3
    }

    init {

        val initialMediaRequest = MediaRequest.Initial.copy(
            limit = LOAD_LIMIT,
            prefetchDistance = PREFETCH_DISTANCE,
            mediaTypes = MediaType.values().asList()
        )

        savedStateHandle.get<String>(ListRouteMediaType)
            ?.takeIf(String::isNotBlank)
            ?.let(MediaType::valueOf)
            ?.let { mediaType ->
                dispatch(Intent.FetchMedia(initialMediaRequest.copy(mediaTypes = listOf(mediaType))))
            } ?: dispatch(Intent.FetchMedia(initialMediaRequest))
    }

    override fun actionToResult(action: Action): Flow<Result> = when (action) {
        is Action.FetchMedia -> flowOf(
            Result.FetchMediaResult(mediaRepositoryImpl.fetchMedia(action.request))
        )
    }

    override fun intentToAction(intent: Intent): Action = when (intent) {
        is Intent.FetchMedia -> Action.FetchMedia(intent.request)
    }

    override fun reducer(state: State, result: Result): State = when (result) {
        is Result.FetchMediaResult -> state.copy(mediaPager = result.data)
    }
}
