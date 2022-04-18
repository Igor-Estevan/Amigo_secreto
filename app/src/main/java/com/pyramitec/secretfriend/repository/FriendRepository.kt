package com.pyramitec.secretfriend.repository

import androidx.annotation.WorkerThread
import com.pyramitec.secretfriend.infra.database.FriendDAO
import com.pyramitec.secretfriend.infra.webservice.SecretFriendAPI
import com.pyramitec.secretfriend.infra.webservice.SecretFriendService
import com.pyramitec.secretfriend.model.Friend
import kotlinx.coroutines.*

class FriendRepository(private val friendDAO: FriendDAO) {

    val allFriends = friendDAO.getAllFriends()

    private val secretFriendService: SecretFriendService by lazy {
        SecretFriendAPI.secretFriendService
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun save(friend: Friend) {
        friendDAO.save(friend)
    }

    suspend fun deleteFriend(friend: Friend) {
        this.friendDAO.delete(friend)
    }

    @ExperimentalCoroutinesApi
    suspend fun doFriendsSecretKeeper() = withContext(Dispatchers.IO) {

        val friends = friendDAO.getAllFriendsList()

        if (friends.size < 2) throw Exception("Deve haver pelo menos 2 participantes no sorteio")

        val friendNames = friends.map { it.name }

        val secretFriendsKeeperResponse = secretFriendService.keeper(friendNames)

        if (secretFriendsKeeperResponse.isSuccessful) {
            secretFriendsKeeperResponse.body()?.also {
                saveSecretFriends(friends, it)
            }
        } else {
            throw Exception(secretFriendsKeeperResponse.message())
        }
    }

    private suspend fun saveSecretFriends(
        friends: List<Friend>,
        secretFriendKeeper: HashMap<String, String>
    ) {
        friends.forEach {
            it.secretFriend = secretFriendKeeper[it.name]
            save(it)
        }
    }

}