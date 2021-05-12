package com.team28.thehiker.SharedPreferenceHandler

import android.app.Activity
import android.content.Context
import com.team28.thehiker.Constants.Constants
import java.time.LocalDate
import java.util.*

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
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE)
        return sharedPref.getInt(Constants.SharedPreferenceConstants.STEP_COUNT, 0)
    }

    override fun setSavedStepCount(context: Activity, numberOfSteps: Int) {
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt(Constants.SharedPreferenceConstants.STEP_COUNT, numberOfSteps)
            apply()
        }
    }

    override fun getLastStepCountUpdate(context: Activity): String? {
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE)
        return sharedPref.getString(Constants.SharedPreferenceConstants.LOCAL_DATE, "0")
    }

    override fun setLastStepCountUpdate(context: Activity, lastStepUpdate: String) {
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(Constants.SharedPreferenceConstants.LOCAL_DATE, lastStepUpdate)
            apply()
        }
    }

}