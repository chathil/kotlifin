package com.chathil.kotlifin.data.repository.show

import com.chathil.kotlifin.data.api.JellyfinSDKPagingSource
import com.chathil.kotlifin.data.dto.extension.asEpisodeMediaSnippet
import com.chathil.kotlifin.data.dto.request.show.ShowNextUpRequest
import com.chathil.kotlifin.data.model.media.MediaSnippet
import kotlinx.coroutines.flow.Flow
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.client.Response
import org.jellyfin.sdk.api.client.extensions.tvShowsApi
import org.jellyfin.sdk.model.api.BaseItemDto
import org.jellyfin.sdk.model.api.BaseItemDtoQueryResult
import org.jellyfin.sdk.model.api.ImageType

class ShowNextUpPagingSource(
    api: () -> Flow<ApiClient>,
    private val request: ShowNextUpRequest
) : JellyfinSDKPagingSource<MediaSnippet.Episode>(api, request) {

    override fun mapResponse(response: BaseItemDto): MediaSnippet.Episode {
        return response.asEpisodeMediaSnippet(baseUrl, imageType = ImageType.BACKDROP)
    }

    override suspend fun invokeApiCall(
        api: ApiClient,
        params: LoadParams<Int>
    ): Response<BaseItemDtoQueryResult> {
        return api.userId?.let { uid ->
            val currentIndex = params.key ?: 0
            val itemRequest = request.asShowNextUpRequest(userId = uid)
                .copy(startIndex = currentIndex)
            api.tvShowsApi.getNextUp(request = itemRequest)
        } ?: throw NullPointerException("Missing uid in the request")
    }
}
