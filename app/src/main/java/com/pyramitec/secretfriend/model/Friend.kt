package com.pyramitec.secretfriend.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Friend(val name: String, val email: String, val phone: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var secretFriend: String? = null

    fun hasSecretFriend() = !secretFriend.isNullOrBlank()

}