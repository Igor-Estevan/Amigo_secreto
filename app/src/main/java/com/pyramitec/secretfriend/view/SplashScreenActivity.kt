package com.pyramitec.secretfriend.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pyramitec.secretfriend.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbGHM8jf1drzKNAtzFYdPGjX6-hS3ojvulNA&usqp=CAU").into(giftsIV)

        GlobalScope.launch {
            delay(5000)
            startActivity(intent)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}