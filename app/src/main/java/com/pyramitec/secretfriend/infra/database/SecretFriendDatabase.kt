package com.pyramitec.secretfriend.infra.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pyramitec.secretfriend.model.Friend
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Friend::class],
    version = 1,
    exportSchema = false
)
abstract class SecretFriendDatabase : RoomDatabase() {

    abstract fun friendDAO(): FriendDAO

    companion object {
        @Volatile
        private var INSTANCE: SecretFriendDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): SecretFriendDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SecretFriendDatabase::class.java,
                    "BusinessControllDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}