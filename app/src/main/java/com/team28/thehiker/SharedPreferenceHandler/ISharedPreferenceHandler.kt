package com.team28.thehiker.SharedPreferenceHandler

import android.app.Activity
import android.content.Context

interface ISharedPreferenceHandler {

    fun getLocalizationString(context: Activity) : String?

    fun setLocalizationString(context: Activity, localizationString: String)
}