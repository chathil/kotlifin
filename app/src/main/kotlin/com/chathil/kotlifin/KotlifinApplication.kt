package com.chathil.kotlifin

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import org.jellyfin.sdk.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class KotlifinApplication: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree());
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .run {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger(Log.VERBOSE))
                } else {
                    this
                }
                this
            }
            .allowRgb565(true)
            .build()
    }
}