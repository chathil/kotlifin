package com.chathil.kotlifin.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chathil.kotlifin.data.room.entity.JellyfinServerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JellyfinServerDao {
    @Query("SELECT * FROM jellyfin_server")
    fun getAllServers(): Flow<List<JellyfinServerEntity>>

    @Query("SELECT * FROM jellyfin_server where id = :id")
    fun loadServerById(id: String): Flow<JellyfinServerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(server: JellyfinServerEntity)

    @Query("DELETE FROM jellyfin_server WHERE id = :id")
    suspend fun deleteServerById(id: String)
}
