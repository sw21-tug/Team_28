package com.team28.thehiker.SharedPreferenceHandler

import android.app.Activity
import android.content.Context
import com.team28.thehiker.Constants.Constants
import java.time.LocalDate

class SharedPreferenceHandler : ISharedPreferenceHandler {

    override fun getLocalizationString(context: Activity): String? {
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE)
        return sharedPref.getString(Constants.SharedPreferenceConstants.LOCALIZATION, "ok")
    }

    override fun setLocalizationString(context: Activity, localizationString: String) {
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(Constants.SharedPreferenceConstants.LOCALIZATION, localizationString)
            apply()
        }
    }

    override fun getSavedStepCount(context: Activity): Int {
        TODO("Not yet implemented")
    }

    override fun setSavedStepCount(context: Activity, numberOfSteps: Int) {
        TODO("Not yet implemented")
    }

    override fun getLastStepCountUpdate(context: Activity): LocalDate {
        TODO("Not yet implemented")
    }

    override fun setLastStepCountUpdate(context: Activity, lastStepUpdate: LocalDate) {
        TODO("Not yet implemented")
    }

}