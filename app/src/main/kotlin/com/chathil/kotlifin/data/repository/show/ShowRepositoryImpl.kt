package com.chathil.kotlifin.data.repository.show

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.chathil.kotlifin.data.dto.request.show.ShowNextUpRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet
import com.chathil.kotlifin.data.repository.base.BaseRepository
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import org.jellyfin.sdk.Jellyfin
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    jellyfin: Jellyfin,
    activeSession: ActiveSessionDataStore
): ShowRepository, BaseRepository(jellyfin, activeSession) {

    companion object {
        private const val JUMP_THRESHOLD_MULTIPLIER = 3
    }
    override fun fetchNextUp(request: ShowNextUpRequest): Pager<Int, MediaSnippet.Episode> {
        return Pager(
            config = PagingConfig(
                pageSize = request.limit,
                prefetchDistance = request.prefetchDistance,
                jumpThreshold = JUMP_THRESHOLD_MULTIPLIER * request.limit
            )
        ) { ShowNextUpPagingSource(::api, request) }
    }
}
