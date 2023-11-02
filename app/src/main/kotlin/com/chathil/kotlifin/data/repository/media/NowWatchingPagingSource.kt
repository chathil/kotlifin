package com.chathil.kotlifin.data.repository.media

import com.chathil.kotlifin.data.api.JellyfinSDKPagingSource
import com.chathil.kotlifin.data.dto.extension.asNowWatching
import com.chathil.kotlifin.data.dto.request.media.NowWatchingRequest
import com.chathil.kotlifin.data.model.media.NowWatching
import kotlinx.coroutines.flow.Flow
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.Response
import org.jellyfin.sdk.api.client.extensions.itemsApi
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemDtoQueryResult

class NowWatchingPagingSource(
    api: () -> Flow<ApiClient>,
    request: NowWatchingRequest
) : JellyfinSDKPagingSource<NowWatching>(api, request) {
    override fun mapResponse(response: BaseItemDto): NowWatching {
        return response.asNowWatching(baseUrl)
    }

    override suspend fun invokeApiCall(api: ApiClient): Response<BaseItemDtoQueryResult> {
        return api.userId?.let { uid ->
            api.itemsApi.getResumeItems(userId = uid)
        } ?: throw NullPointerException("Missing uid in the request")
    }
}
