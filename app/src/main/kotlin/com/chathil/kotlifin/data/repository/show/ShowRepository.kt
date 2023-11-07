package com.chathil.kotlifin.data.repository.show

import androidx.paging.Pager
import com.chathil.kotlifin.data.dto.request.show.ShowNextUpRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet

interface ShowRepository {
    fun fetchNextUp(request: ShowNextUpRequest): Pager<Int, MediaSnippet.Episode>
}
