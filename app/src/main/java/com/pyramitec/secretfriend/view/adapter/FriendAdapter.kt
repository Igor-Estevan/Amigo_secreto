package com.pyramitec.secretfriend.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pyramitec.secretfriend.databinding.ItemFriendBinding
import com.pyramitec.secretfriend.model.Friend

class FriendAdapter(
    var friends: List<Friend>,
    private val shareOnClick: (friend: Friend) -> Unit,
    private val deleteOnClick: (friend: Friend) -> Unit
) :
    RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(friends[position], shareOnClick, deleteOnClick)
    }

    override fun getItemCount(): Int = friends.size

    class ViewHolder(private val itemFriendBinding: ItemFriendBinding) :
        RecyclerView.ViewHolder(itemFriendBinding.root) {
        fun bind(
            item: Friend,
            shareOnClick: (friend: Friend) -> Unit,
            deleteOnClick: (friend: Friend) -> Unit
        ) {
            itemFriendBinding.friend = item
            itemFriendBinding.shareBtn.setOnClickListener { shareOnClick(item)}
            itemFriendBinding.deleteBtn.setOnClickListener { deleteOnClick(item)}
            itemFriendBinding.executePendingBindings()
        }
    }
}