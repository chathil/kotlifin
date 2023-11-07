package com.chathil.kotlifin.ui.feature.home.mvi

import androidx.paging.Pager
import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.session.ActiveSession

data class State(
    val isLatestMediaLoading: Map<MediaType, Boolean>,
    val latestMedia: Map<MediaType, List<MediaSnippet>>,
    val isNowWatchingLoading: Boolean,
    val isShowNextUpLoading: Boolean,
    val nowWatchingPager: Pager<Int, MediaSnippet>?,
    val showNextUpPager: Pager<Int, MediaSnippet.Episode>?,
    val latestMediaLoadError: Map<MediaType, Throwable>,
    val activeSession: ActiveSession,
    val error: Throwable?
) {
    companion object {
        val Initial = State(
            isLatestMediaLoading = mapOf(
                MediaType.MOVIE to false,
                MediaType.SHOW to false
            ),
            isNowWatchingLoading = false,
            isShowNextUpLoading = false,
            nowWatchingPager = null,
            showNextUpPager = null,
            latestMedia = emptyMap(),
            latestMediaLoadError = emptyMap(),
            activeSession = ActiveSession.Empty,
            error = null,
        )
    }
}
