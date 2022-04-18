package com.pyramitec.secretfriend

import android.app.Application
import com.pyramitec.secretfriend.infra.database.SecretFriendDatabase
import com.pyramitec.secretfriend.repository.FriendRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SecretFriendApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database: SecretFriendDatabase by lazy {
        SecretFriendDatabase.getDatabase(this, applicationScope)
    }

    val friendRepository: FriendRepository by lazy {
        FriendRepository(database.friendDAO())
    }
}