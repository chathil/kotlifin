package com.chathil.kotlifin.data.dto.request.movie

import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.dto.request.media.OrderBy
import com.chathil.kotlifin.data.dto.request.media.SortBy
import com.chathil.kotlifin.data.dto.request.paged.JellyfinPagedRequest
import org.jellyfin.sdk.model.api.request.GetItemsRequest
import org.jellyfin.sdk.model.serializer.toUUID

data class MediaRequest(
    override val startIndex: Int,
    override val orderBy: OrderBy = OrderBy.DATE_CREATED,
    override val sortBy: SortBy = SortBy.DESCENDING,
    override val prefetchDistance: Int = 1,
    override val limit: Int = 10,
    val mediaTypes: List<MediaType> = emptyList(),
    val keyword: String = "",
    val selectedGenres: Map<String, String> = emptyMap()
) : JellyfinPagedRequest() {

    companion object {
        val Initial = MediaRequest(startIndex = 0, mediaTypes = emptyList(), keyword = "", selectedGenres = emptyMap())
    }

    fun asGetItemsRequest(): GetItemsRequest {
        return GetItemsRequest(
            sortOrder = listOf(SortBy.toJellyfinSortOrder(sortBy)),
            sortBy = listOf(orderBy.rawValue),
            startIndex = startIndex,
            limit = limit,
            includeItemTypes = mediaTypes.ifEmpty { supportedMediaTypes }.map(MediaType::toJellyfinMediaType),
            searchTerm = keyword,
            genreIds = selectedGenres.keys.map(String::toUUID),
            recursive = true // Search within folders
        )
    }
}