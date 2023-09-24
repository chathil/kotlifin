package com.chathil.kotlifin.ui.feature.home.mvi

import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.vo.Resource

sealed interface Result {
    data class LoadLatestMediaResult(
        val data: Resource<List<MediaSnippet>>,
        val mediaType: MediaType
    ) : Result
    data class SaveActiveSession(
        val session: ActiveSession
    ) : Result
}
