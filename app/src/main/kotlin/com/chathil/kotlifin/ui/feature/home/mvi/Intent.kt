package com.chathil.kotlifin.ui.feature.home.mvi

import com.chathil.kotlifin.data.dto.request.media.LatestMediaRequest
import com.chathil.kotlifin.data.model.session.ActiveSession

sealed interface Intent {
    data class LoadLatestMedia(val request: LatestMediaRequest) : Intent
    data class SaveActiveSession(val session: ActiveSession) : Intent
}
