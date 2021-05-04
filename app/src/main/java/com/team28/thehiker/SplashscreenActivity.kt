package com.team28.thehiker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import com.team28.thehiker.language.LanguageSelector

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //LanguageSelector.setLocaleToRussian(this)
        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.hide()

        displaySplashscreen()
    }

    fun displaySplashscreen() {
        Handler().postDelayed({
            val intent = Intent(this@SplashscreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}