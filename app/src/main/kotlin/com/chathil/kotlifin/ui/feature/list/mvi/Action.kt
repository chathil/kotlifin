package com.chathil.kotlifin.ui.feature.list.mvi

import com.chathil.kotlifin.data.dto.request.movie.MediaRequest

sealed interface Action {
    data class FetchMedia(val request: MediaRequest) : Action
}