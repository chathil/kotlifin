package com.chathil.kotlifin.data.dto.request.paged

import com.chathil.kotlifin.data.dto.request.media.MediaType
import com.chathil.kotlifin.data.dto.request.media.OrderBy
import com.chathil.kotlifin.data.dto.request.media.SortBy
import org.jellyfin.sdk.model.api.request.GetItemsRequest

abstract class JellyfinPagedRequest(
    open val startIndex: Int = 0,
    open val orderBy: OrderBy = OrderBy.DATE_CREATED,
    open val sortBy: SortBy = SortBy.DESCENDING,
    open val prefetchDistance: Int = 1,
    open val limit: Int = 10,
    val supportedMediaTypes: List<MediaType> = MediaType.values().asList()
)
