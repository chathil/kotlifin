package com.chathil.kotlifin.ui.feature.list.mvi

import com.chathil.kotlifin.data.dto.request.movie.MediaRequest

sealed interface Intent {
    data class FetchMedia(val request: MediaRequest) : Intent
}