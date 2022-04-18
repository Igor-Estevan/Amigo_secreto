package com.pyramitec.secretfriend.viewmodel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pyramitec.secretfriend.model.Friend
import com.pyramitec.secretfriend.repository.FriendRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class FriendViewModel(private val friendRepository: FriendRepository) : BaseViewModel() {

    val allFriends = friendRepository.allFriends.asLiveData()

    @Bindable
    var name: String = ""

    @Bindable
    var email: String = ""

    @Bindable
    var phone: String = ""

    val isLoading = MutableLiveData(false)

    fun formIsValid(): Boolean =
        this.name.isNotBlank() &&
                this.email.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(this.email).matches() &&
                this.phone.isNotBlank() &&
                Patterns.PHONE.matcher(this.phone).matches() &&
                this.phone.length == 15

    fun save() {
        if (formIsValid()) {
            viewModelScope.launch {
                friendRepository.save(Friend(name, email, phone))
            }
        }
    }

    fun delete(friend: Friend) {
        viewModelScope.launch {
            friendRepository.deleteFriend(friend)
        }
    }

    fun undelete(friend: Friend) {
        viewModelScope.launch {
            friendRepository.save(friend)
        }
    }

    @ExperimentalCoroutinesApi
    fun doKeeper(context: Context) = viewModelScope.launch {
        isLoading.postValue(true)
        try {
            friendRepository.doFriendsSecretKeeper()
            Toast.makeText(context, "Sorteio realizado com sucesso", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Não foi possível realizar o sorteio: ${e.message}", Toast.LENGTH_LONG).show()
        }
        isLoading.postValue(false)
    }

}