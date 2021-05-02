package com.team28.thehiker.language

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class LanguageSelector {

    companion object {
        fun setLocaleToRussian(context: Context){
            val locale = Locale("ru", "RU")

            val config = Configuration(context.resources.configuration)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale)
            } else {
                config.locale = locale
            }

            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
        fun setLocaleToEnglish(context: Context){
        }
    }
}