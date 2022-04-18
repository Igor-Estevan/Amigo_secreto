package com.pyramitec.secretfriend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pyramitec.secretfriend.SecretFriendApplication

class FriendViewModelFactory(private val application: SecretFriendApplication) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FriendViewModel(application.friendRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}