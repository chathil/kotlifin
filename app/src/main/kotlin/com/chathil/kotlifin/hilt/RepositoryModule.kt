package com.chathil.kotlifin.hilt

import com.chathil.kotlifin.data.repository.media.MediaRepository
import com.chathil.kotlifin.data.repository.media.MediaRepositoryImpl
import com.chathil.kotlifin.data.repository.server.ServerRepository
import com.chathil.kotlifin.data.repository.server.ServerRepositoryImpl
import com.chathil.kotlifin.data.repository.show.ShowRepository
import com.chathil.kotlifin.data.repository.show.ShowRepositoryImpl
import com.chathil.kotlifin.data.repository.user.UserRepository
import com.chathil.kotlifin.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun provideServerRepository(repository: ServerRepositoryImpl): ServerRepository

    @Binds
    fun provideUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    fun provideMediaRepository(repository: MediaRepositoryImpl): MediaRepository

    @Binds
    fun provideShowRepository(repository: ShowRepositoryImpl): ShowRepository
}
