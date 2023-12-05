package com.chathil.kotlifin.data.repository.media

import androidx.paging.Pager
import com.chathil.kotlifin.data.dto.request.media.LatestMediaRequest
import com.chathil.kotlifin.data.dto.request.movie.MediaRequest
import com.chathil.kotlifin.data.dto.request.media.NowWatchingRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun fetchNowWatching(request: NowWatchingRequest): Pager<Int, MediaSnippet>

    fun fetchRecentlyAdded(): Flow<Resource<MediaSnippet>>

    fun fetchMedia(request: MediaRequest): Pager<Int, MediaSnippet>

    fun fetchLatestMedia(request: LatestMediaRequest): Flow<Resource<List<MediaSnippet>>>

    fun fetchLatestShows(): Flow<Resource<MediaSnippet>>
}
