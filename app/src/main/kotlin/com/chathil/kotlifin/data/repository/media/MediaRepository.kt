package com.chathil.kotlifin.data.repository.media

import androidx.paging.Pager
import com.chathil.kotlifin.data.dto.request.media.LatestMediaRequest
import com.chathil.kotlifin.data.dto.request.movie.LatestMoviesRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.media.NowWatching
import com.chathil.kotlifin.data.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    fun fetchNowWatching(): Flow<Resource<NowWatching>>

    fun fetchRecentlyAdded(): Flow<Resource<MediaSnippet>>

    fun fetchLatestMovies(request: LatestMoviesRequest): Pager<Int, MediaSnippet>

    fun fetchLatestMedia(request: LatestMediaRequest): Flow<Resource<List<MediaSnippet>>>

    fun fetchLatestShows(): Flow<Resource<MediaSnippet>>
}
