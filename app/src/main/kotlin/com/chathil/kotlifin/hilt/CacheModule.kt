package com.chathil.kotlifin.hilt

import com.chathil.kotlifin.data.cache.InMemoryCache
import com.chathil.kotlifin.data.cache.NewSessionInMemoryCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface CacheModule {
    @Binds
    fun provideNewSessionInMemoryCache(inMemoryCache: NewSessionInMemoryCache): InMemoryCache<String, String>
}