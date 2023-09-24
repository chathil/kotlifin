package com.chathil.kotlifin.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

fun <K : Any, V : Any> List<V>.asPagerData(
    pageSize: Int = 5,
    prefetchDistance: Int = 1,
    jumpThreshold: Int = 15
): Pager<K, V> {
    return Pager(
        config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = prefetchDistance,
            jumpThreshold = jumpThreshold
        )
    ) {
        object : PagingSource<K, V>() {
            override val jumpingSupported: Boolean = true

            override val keyReuseSupported: Boolean = true

            override fun getRefreshKey(state: PagingState<K, V>): K? = null

            override suspend fun load(params: LoadParams<K>): LoadResult<K, V> {
                return LoadResult.Page(this@asPagerData, null, null, 0, this@asPagerData.size)
            }
        }
    }
}
