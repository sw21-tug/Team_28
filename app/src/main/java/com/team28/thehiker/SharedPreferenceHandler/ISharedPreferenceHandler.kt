package com.team28.thehiker.SharedPreferenceHandler

import android.content.Context

interface ISharedPreferenceHandler {

    fun getLocalizationString(context: Context) : String

    fun setLocalizationString(context: Context, localizationString: String)
}