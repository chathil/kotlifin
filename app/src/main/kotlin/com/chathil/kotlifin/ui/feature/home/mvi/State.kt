package com.chathil.kotlifin.ui.feature.home.mvi

import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.session.ActiveSession

data class State(
    val isMediaLoading: Map<MediaType, Boolean>,
    val latestMedia: Map<MediaType, List<MediaSnippet>>,
    val activeSession: ActiveSession,
    val error: Throwable?
) {
    companion object {
        val Initial = State(
            isMediaLoading = mapOf(
                MediaType.MOVIE to false,
                MediaType.TV_SHOW to false
            ),
            latestMedia = emptyMap(),
            activeSession = ActiveSession.Empty,
            error = null
        )
    }
}
