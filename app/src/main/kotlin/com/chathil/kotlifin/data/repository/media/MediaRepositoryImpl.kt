package com.chathil.kotlifin.data.repository.media

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.chathil.kotlifin.data.dto.extension.asMediaSnippet
import com.chathil.kotlifin.data.dto.request.media.LatestMediaRequest
import com.chathil.kotlifin.data.dto.request.media.toJellyfinRequest
import com.chathil.kotlifin.data.dto.request.movie.LatestMoviesRequest
import com.chathil.kotlifin.data.dto.request.media.NowWatchingRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.model.media.NowWatching
import com.chathil.kotlifin.data.repository.base.BaseRepository
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.data.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.api.client.extensions.userLibraryApi
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    jellyfin: Jellyfin,
    private val activeSession: ActiveSessionDataStore
) : MediaRepository, BaseRepository(jellyfin, activeSession) {

    companion object {
        private const val JUMP_THRESHOLD_MULTIPLIER = 3
    }

    override fun fetchNowWatching(request: NowWatchingRequest): Pager<Int, NowWatching> {
        return Pager(
            config = PagingConfig(
                pageSize = request.limit,
                prefetchDistance = request.prefetchDistance,
                jumpThreshold = JUMP_THRESHOLD_MULTIPLIER * request.limit
            )
        ) { NowWatchingPagingSource(::api, request) }
    }

    override fun fetchRecentlyAdded(): Flow<Resource<MediaSnippet>> {
        TODO("Not yet implemented")
    }

    override fun fetchLatestMovies(request: LatestMoviesRequest): Pager<Int, MediaSnippet> {
        return Pager(
            config = PagingConfig(
                pageSize = request.limit,
                prefetchDistance = request.prefetchDistance,
                jumpThreshold = JUMP_THRESHOLD_MULTIPLIER * request.limit
            )
        ) { MediaSnippetPagingSource(::api, request) }
    }

    override fun fetchLatestMedia(request: LatestMediaRequest): Flow<Resource<List<MediaSnippet>>> {
        return activeSession.activeSession.flatMapLatest { session ->
            api().map { client ->
                client.userLibraryApi.getLatestMedia(request.toJellyfinRequest(session.userId))
            }.map { response ->
                response.content.map { item -> item.asMediaSnippet(session.serverPublicAddress) }
            }
        }.map<List<MediaSnippet>, Resource<List<MediaSnippet>>> { items ->
            Resource.Success(items)
        }.onStart { emit(Resource.Loading()) }
            .catch { error -> emit(Resource.Error(error)) }
    }

    override fun fetchLatestShows(): Flow<Resource<MediaSnippet>> {
        TODO("Not yet implemented")
    }

}