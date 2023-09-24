package com.chathil.kotlifin.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chathil.kotlifin.data.room.entity.JellyfinUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JellyfinUserDao {
    @Query("SELECT * FROM jellyfin_user")
    fun getAllUsers(): Flow<List<JellyfinUserEntity>>

    @Query("SELECT * FROM jellyfin_user where id = :id")
    fun loadUserById(id: String): Flow<JellyfinUserEntity>

    @Query ("SELECT * FROM jellyfin_user where server_id = :id")
    fun loadUsersByServerId(id: String): Flow<List<JellyfinUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: JellyfinUserEntity)

    @Query("DELETE FROM jellyfin_user WHERE id = :id")
    suspend fun deleteUserById(id: String)

    @Query("DELETE FROM jellyfin_user WHERE server_id = :id")
    suspend fun deleteUserByServerId(id: String)
}
