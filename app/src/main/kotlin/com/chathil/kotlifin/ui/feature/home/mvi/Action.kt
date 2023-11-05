package com.chathil.kotlifin.ui.feature.home.mvi

import com.chathil.kotlifin.data.dto.request.media.LatestMediaRequest
import com.chathil.kotlifin.data.dto.request.media.NowWatchingRequest
import com.chathil.kotlifin.data.dto.request.show.ShowNextUpRequest
import com.chathil.kotlifin.data.model.session.ActiveSession

sealed interface Action {
    data class LoadLatestMedia(val request: LatestMediaRequest) : Action
    data class SaveActiveSession(val session: ActiveSession) : Action
    data class LoadNowWatching(val request: NowWatchingRequest) : Action
    data class LoadShowNextUp(val request: ShowNextUpRequest) : Action
}
