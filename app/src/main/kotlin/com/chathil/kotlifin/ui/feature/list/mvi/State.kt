package com.chathil.kotlifin.ui.feature.list.mvi

import androidx.paging.Pager
import com.chathil.kotlifin.data.dto.request.movie.MediaRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet

data class State(
    val mediaPager: Pager<Int, MediaSnippet>?,
    val mediaRequest: MediaRequest,
    val error: Throwable?
) {
    companion object {
        val Initial = State(mediaPager = null, mediaRequest = MediaRequest.Initial, error = null)
    }
}
