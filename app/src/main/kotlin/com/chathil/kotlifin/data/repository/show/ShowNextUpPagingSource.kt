package com.chathil.kotlifin.data.repository.show

import com.chathil.kotlifin.data.api.JellyfinSDKPagingSource
import com.chathil.kotlifin.data.dto.extension.asShowNextUp
import com.chathil.kotlifin.data.dto.request.show.ShowNextUpRequest
import com.chathil.kotlifin.data.model.show.ShowNextUp
import kotlinx.coroutines.flow.Flow
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.extensions.tvShowsApi
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemDtoQueryResult

class ShowNextUpPagingSource(
    api: () -> Flow<ApiClient>,
    request: ShowNextUpRequest
) : JellyfinSDKPagingSource<ShowNextUp>(api, request) {

    override fun mapResponse(response: BaseItemDto): ShowNextUp {
        return response.asShowNextUp(baseUrl)
    }

    override suspend fun invokeApiCall(api: ApiClient): BaseItemDtoQueryResult {
        return api.userId?.let { uid ->
            api.tvShowsApi.getNextUp(userId = uid).content
        } ?: throw NullPointerException("Missing uid in the request")
    }
}
