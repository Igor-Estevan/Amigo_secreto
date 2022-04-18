package com.pyramitec.secretfriend.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pyramitec.secretfriend.R
import com.pyramitec.secretfriend.SecretFriendApplication
import com.pyramitec.secretfriend.model.Friend
import com.pyramitec.secretfriend.view.FriendActivity
import com.pyramitec.secretfriend.view.adapter.FriendAdapter
import com.pyramitec.secretfriend.viewmodel.FriendViewModel
import com.pyramitec.secretfriend.viewmodel.FriendViewModelFactory
import kotlinx.android.synthetic.main.fragment_friend.view.*
import java.lang.Exception

class FriendFragment : Fragment() {

    private val friendViewModel: FriendViewModel by viewModels {
        FriendViewModelFactory(requireActivity().application as SecretFriendApplication)
    }

    lateinit var adapter: FriendAdapter

    var hasItemOnList: MutableLiveData<Boolean> = MutableLiveData(false)

    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_friend, container, false)

        root.keeperBtn.setOnClickListener(this::doSecretFriendKeeper)
        root.addFriendFragmentBtn.addFriendFragmentBtn.setOnClickListener(this::addNewFriend)
        progressBar = root.progressBar

        val recyclerView: RecyclerView = root.findViewById(R.id.accounts_recyclerview)

        adapter = FriendAdapter(emptyList(), this::shareOnClick, this::deleteOnClick)

        recyclerView.adapter = adapter

        return root
    }

    override fun onResume() {
        friendViewModel.allFriends.observe(viewLifecycleOwner) {
            adapter.friends = it
            adapter.notifyDataSetChanged()
            hasItemOnList.postValue(it.isNotEmpty())
        }

        friendViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }

        super.onResume()
    }

    private fun shareOnClick(friend: Friend) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "${friend.name} clique no link e descubra qual é o seu amigo secreto: ${friend.secretFriend}"
            )
            putExtra(Intent.EXTRA_EMAIL, friend.email)
            putExtra(Intent.EXTRA_PHONE_NUMBER, friend.phone)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun deleteOnClick(friend: Friend) {
        friendViewModel.delete(friend)
            Snackbar.make(this.requireView(), "Amigo excluído", Snackbar.LENGTH_LONG)
                .setAction("Desfazer") {
                    friendViewModel.undelete(friend)
                }
                .show()
    }

    private fun addNewFriend(view: View) {
        val intent = Intent(view.context, FriendActivity::class.java)
        startActivity(intent)
    }

    private fun doSecretFriendKeeper(view: View) {
        try {
            this.friendViewModel.doKeeper(view.context)
        } catch (e: Exception) {
            Toast.makeText(
                this.activity,
                "Erro ao realizar sorteio: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}