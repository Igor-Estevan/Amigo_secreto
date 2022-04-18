package com.pyramitec.secretfriend.infra.database

import androidx.room.*
import com.pyramitec.secretfriend.model.Friend
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDAO {

    @Transaction
    @Query("SELECT * FROM Friend ORDER BY name ASC")
    fun getAllFriends(): Flow<List<Friend>>

    @Transaction
    @Query("SELECT * FROM Friend ORDER BY name ASC")
    suspend fun getAllFriendsList(): List<Friend>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(friend: Friend)

    @Delete
    suspend fun delete(friend: Friend)

}