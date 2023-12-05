package com.chathil.kotlifin.ui.feature.list.mvi

import androidx.paging.Pager
import com.chathil.kotlifin.data.model.media.MediaSnippet

sealed interface Result {
    data class FetchMediaResult(val data: Pager<Int, MediaSnippet>) : Result
}