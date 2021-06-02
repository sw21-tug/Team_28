package com.team28.thehiker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.team28.thehiker.sharedpreferencehandler.SharedPreferenceHandler
import com.team28.thehiker.language.LanguageSelector

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = SharedPreferenceHandler().getLocalizationString(this)
        if (language.equals("ru")){
            LanguageSelector.setLocaleToRussian(this)
        }else{
            LanguageSelector.setLocaleToEnglish(this)
        }

        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.hide()

        displaySplashscreen()
    }

    private fun displaySplashscreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashscreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}