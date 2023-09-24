package com.chathil.kotlifin.data.dto.request.media

import org.jellyfin.sdk.model.api.request.GetLatestMediaRequest
import org.jellyfin.sdk.model.serializer.toUUID

data class LatestMediaRequest(
    val mediaType: MediaType,
    val limit: Int = 20
)

fun LatestMediaRequest.toJellyfinRequest(userId: String): GetLatestMediaRequest {
    return GetLatestMediaRequest(
        userId = userId.toUUID(),
        includeItemTypes = listOf(MediaType.toJellyfinMediaType(mediaType)),
        groupItems = false,
    )
}
