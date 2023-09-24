package com.chathil.kotlifin.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chathil.kotlifin.data.room.dao.JellyfinServerDao
import com.chathil.kotlifin.data.room.dao.JellyfinUserDao
import com.chathil.kotlifin.data.room.entity.JellyfinServerEntity
import com.chathil.kotlifin.data.room.entity.JellyfinUserEntity


@Database(
    entities = [JellyfinServerEntity::class, JellyfinUserEntity::class],
    version = 1
)
abstract class KotlifinDb : RoomDatabase() {
    abstract fun serverDao(): JellyfinServerDao
    abstract fun userDao(): JellyfinUserDao
}
