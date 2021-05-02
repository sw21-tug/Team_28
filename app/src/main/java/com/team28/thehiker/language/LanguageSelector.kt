package com.team28.thehiker.language

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

class LanguageSelector {

    companion object {
        fun setLocaleToRussian(context: Context) {
            setLocale(context, Locale("ru", "RU"))
        }


        fun setLocaleToEnglish(context: Context) {
            setLocale(context, Locale("en", "EN"))
        }

        private fun setLocale(context: Context, locale: Locale) {
            val config = Configuration(context.resources.configuration)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale)
            } else {
                config.locale = locale
            }
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
}