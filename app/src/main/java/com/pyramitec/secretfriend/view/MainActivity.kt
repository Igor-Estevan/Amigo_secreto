package com.pyramitec.secretfriend.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pyramitec.secretfriend.R
import com.pyramitec.secretfriend.view.fragment.FriendFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val friendFragment = FriendFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.friendFragmentContainer, friendFragment)
                .commitNow()
        }
    }

    override fun onResume() {
        friendFragment.hasItemOnList.observe(this) {
            if (it) {
                friendFragmentContainer.visibility = View.VISIBLE
                addFriendBtn.visibility = View.GONE
            } else {
                friendFragmentContainer.visibility = View.GONE
                addFriendBtn.visibility = View.VISIBLE
            }
        }
        super.onResume()
    }

    fun createFriend(view: View) {
        val intent = Intent(this, FriendActivity::class.java)
        startActivity(intent)
    }


}