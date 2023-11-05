package com.chathil.kotlifin.ui.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chathil.kotlifin.data.dto.request.media.LatestMediaRequest
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.dto.request.media.NowWatchingRequest
import com.chathil.kotlifin.data.dto.request.show.ShowNextUpRequest
import com.chathil.kotlifin.data.repository.media.MediaRepository
import com.chathil.kotlifin.data.repository.show.ShowRepository
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.data.vo.Resource
import com.chathil.kotlifin.ui.core.CoreMviViewModel
import com.chathil.kotlifin.ui.feature.home.mvi.Action
import com.chathil.kotlifin.ui.feature.home.mvi.Event
import com.chathil.kotlifin.ui.feature.home.mvi.Intent
import com.chathil.kotlifin.ui.feature.home.mvi.Result
import com.chathil.kotlifin.ui.feature.home.mvi.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    activeSession: ActiveSessionDataStore,
    private val mediaRepository: MediaRepository,
    private val showRepository: ShowRepository,
    savedStateHandle: SavedStateHandle,
) : CoreMviViewModel<Intent, Action, Result, State, Event>(State.Initial, savedStateHandle) {

    companion object {
        const val LOAD_LIMIT = 15
        const val PREFETCH_DISTANCE = 3
    }

    init {
        dispatch(Intent.LoadLatestMedia(LatestMediaRequest(MediaType.MOVIE)))
        dispatch(Intent.LoadLatestMedia(LatestMediaRequest(MediaType.TV_SHOW)))
        dispatch(
            Intent.LoadShowNextUp(
                ShowNextUpRequest.Initial.copy(
                    limit = LOAD_LIMIT, prefetchDistance = PREFETCH_DISTANCE
                )
            )
        )
        dispatch(
            Intent.LoadNowWatching(
                NowWatchingRequest.Initial.copy(
                    limit = LOAD_LIMIT, prefetchDistance = PREFETCH_DISTANCE
                )
            )
        )
        activeSession.activeSession
            .map(Intent::SaveActiveSession)
            .map(::dispatch)
            .launchIn(viewModelScope)
    }

    override fun actionToResult(action: Action): Flow<Result> = when (action) {
        is Action.LoadLatestMedia -> mediaRepository.fetchLatestMedia(action.request)
            .map { result ->
                Result.LoadLatestMediaResult(result, action.request.mediaType)
            }

        is Action.SaveActiveSession -> flowOf(Result.SaveActiveSession(action.session))
        is Action.LoadNowWatching -> flowOf(
            Result.LoadNowWatchingResult(
                mediaRepository.fetchNowWatching(
                    action.request
                )
            )
        )

        is Action.LoadShowNextUp -> flowOf(
            Result.LoadShowNextUpResult(showRepository.fetchNextUp(action.request))
        )
    }

    override fun intentToAction(intent: Intent): Action = when (intent) {
        is Intent.LoadLatestMedia -> Action.LoadLatestMedia(intent.request)
        is Intent.SaveActiveSession -> Action.SaveActiveSession(intent.session)
        is Intent.LoadNowWatching -> Action.LoadNowWatching(intent.request)
        is Intent.LoadShowNextUp -> Action.LoadShowNextUp(intent.request)
    }

    override fun reducer(state: State, result: Result): State = when (result) {
        is Result.LoadLatestMediaResult -> when (result.data) {
            is Resource.Loading -> state.copy(
                isMediaLoading = state.isMediaLoading + mapOf(result.mediaType to true),
                latestMediaLoadError = state.latestMediaLoadError.filterKeys { key -> key != result.mediaType }
            )

            is Resource.Success -> state.copy(
                latestMedia = state.latestMedia + mapOf(result.mediaType to result.data.data),
                isMediaLoading = state.isMediaLoading + mapOf(result.mediaType to false)
            )

            is Resource.Error -> state.copy(
                isMediaLoading = state.isMediaLoading + mapOf(result.mediaType to false),
                latestMediaLoadError = state.latestMediaLoadError + mapOf(result.mediaType to result.data.error)
            )
        }

        is Result.SaveActiveSession -> state.copy(activeSession = result.session)
        is Result.LoadNowWatchingResult -> state.copy(
            isNowWatchingLoading = false,
            error = null,
            nowWatchingPager = result.data
        )

        is Result.LoadShowNextUpResult -> state.copy(
            isShowNextUpLoading = false,
            error = null,
            showNextUpPager = result.data
        )
    }
}
