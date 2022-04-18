package com.pyramitec.secretfriend.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.pyramitec.secretfriend.R
import com.pyramitec.secretfriend.SecretFriendApplication
import com.pyramitec.secretfriend.databinding.ActivityFriendBinding
import com.pyramitec.secretfriend.utils.MaskWatcher
import com.pyramitec.secretfriend.viewmodel.FriendViewModel
import com.pyramitec.secretfriend.viewmodel.FriendViewModelFactory

class FriendActivity : AppCompatActivity() {

    private val friendViewModel: FriendViewModel by viewModels {
        FriendViewModelFactory(application as SecretFriendApplication)
    }

    private lateinit var binding: ActivityFriendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_friend)

        binding.phoneET.addTextChangedListener(MaskWatcher.buildPhone())

        binding.friendViewModel = friendViewModel
    }

    fun saveFriend(view: View) {
        if (this.friendViewModel.formIsValid()) {
            this.friendViewModel.save()
            finish()
        } else {
            if (friendViewModel.name.isBlank()) {
                binding.nameET.error = getString(R.string.name_empty)
            }

            when {
                friendViewModel.email.isBlank() -> binding.emailET.error =
                    getString(R.string.email_empty)
                Patterns.EMAIL_ADDRESS.matcher(friendViewModel.email)
                    .matches() -> binding.emailET.error = getString(R.string.email_invalid)
            }

            when {
                friendViewModel.phone.isBlank() -> binding.phoneET.error =
                    getString(R.string.phone_empty)
                Patterns.PHONE.matcher(friendViewModel.phone).matches() -> binding.phoneET.error =
                    getString(R.string.phone_invalid)
                friendViewModel.phone.length != 15 -> binding.phoneET.error =
                    getString(R.string.phone_invalid)
            }
        }
    }

    fun cancel(view: View) {
        this.onBackPressed()
    }
}