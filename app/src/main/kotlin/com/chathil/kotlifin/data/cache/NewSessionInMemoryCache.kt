package com.chathil.kotlifin.data.cache

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewSessionInMemoryCache @Inject constructor(): InMemoryCache<String, String> {
    val map = mutableMapOf<String, String>()
    override fun exist(key: String): Boolean {
        return map.contains(key)
    }

    override fun fetch(key: String): String? {
        return map[key]
    }

    override fun store(key: String, value: String): String {
        map[key] = value
        return value
    }

    override fun clear(key: String) {
        map.clear()
    }
}
