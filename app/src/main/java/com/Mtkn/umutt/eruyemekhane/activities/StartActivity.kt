package com.Mtkn.umutt.eruyemekhane.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Mtkn.umutt.eruyemekhane.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
