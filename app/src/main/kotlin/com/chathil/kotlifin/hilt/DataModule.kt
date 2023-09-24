package com.chathil.kotlifin.hilt

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.chathil.kotlifin.data.model.session.ActiveSession
import com.chathil.kotlifin.data.model.session.ActiveSessionSerializer
import com.chathil.kotlifin.data.room.KotlifinDb
import com.chathil.kotlifin.data.store.ActiveSessionDataStore
import com.chathil.kotlifin.data.store.ActiveSessionDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.jellyfin.sdk.Jellyfin
import org.jellyfin.sdk.api.client.ApiClient
import org.jellyfin.sdk.api.operations.Api
import org.jellyfin.sdk.createJellyfin
import org.jellyfin.sdk.model.ClientInfo
import org.jellyfin.sdk.model.DeviceInfo
import org.jellyfin.sdk.util.ApiClientFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideKotlifinDb(@ApplicationContext context: Context): KotlifinDb {
        return Room.databaseBuilder(
            context,
            KotlifinDb::class.java, "kotlifin_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideActiveSessionDataStore(@ApplicationContext context: Context): ActiveSessionDataStore {
        val dataStore = DataStoreFactory.create(
            serializer = ActiveSessionSerializer,
            produceFile = { context.dataStoreFile("active_session.pb") },
        )

        return ActiveSessionDataStoreImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideJellyfinSdk(@ApplicationContext appContext: Context): Jellyfin {
        return createJellyfin {
            clientInfo = ClientInfo("Jellyfin Sample: Kotlin CLI", "DEV")
            deviceInfo = DeviceInfo("cli", "cli")
            context = appContext
        }
    }
}
