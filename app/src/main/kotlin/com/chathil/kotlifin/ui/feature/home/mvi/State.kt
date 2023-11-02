package com.chathil.kotlifin.ui.feature.home.mvi

import androidx.paging.Pager
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.media.NowWatching
import com.chathil.kotlifin.data.model.session.ActiveSession

data class State(
    val isMediaLoading: Map<MediaType, Boolean>,
    val latestMedia: Map<MediaType, List<MediaSnippet>>,
    val isNowWatchingLoading: Boolean,
    val isNowWatchingError: Throwable?,
    val nowWatchingPager: Pager<Int, NowWatching>?,
    val latestMediaLoadError: Map<MediaType, Throwable>,
    val activeSession: ActiveSession,
    val error: Throwable?
) {
    companion object {
        val Initial = State(
            isMediaLoading = mapOf(
                MediaType.MOVIE to false,
                MediaType.TV_SHOW to false
            ),
            isNowWatchingLoading = false,
            isNowWatchingError = null,
            nowWatchingPager = null,
            latestMedia = emptyMap(),
            latestMediaLoadError = emptyMap(),
            activeSession = ActiveSession.Empty,
            error = null,
        )
    }
}
