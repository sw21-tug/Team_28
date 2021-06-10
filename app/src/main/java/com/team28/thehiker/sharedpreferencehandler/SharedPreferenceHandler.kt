package com.team28.thehiker.sharedpreferencehandler

import android.app.Activity
import android.content.Context
import com.team28.thehiker.constants.Constants

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
        return sharedPref.getString(Constants.SharedPreferenceConstants.LOCAL_DATE, Constants.SharedPreferenceConstants.LAST_STEPCOUNT_DEFAULT)
    }

    override fun setLastStepCountUpdate(context: Activity, lastStepUpdate: String) {
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(Constants.SharedPreferenceConstants.LOCAL_DATE, lastStepUpdate)
            apply()
        }
    }

    override fun getNumbers(context: Activity): List<String?> {
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE)
        val sos1 = sharedPref.getString(Constants.SharedPreferenceConstants.SOS_NUMBER_1, "")
        val sos2 = sharedPref.getString(Constants.SharedPreferenceConstants.SOS_NUMBER_2, "")
        return listOf(sos1, sos2)
    }

    override fun setNumbers(context: Activity, numbers: List<String>) {
        val sharedPref = context.applicationContext.getSharedPreferences("hiker_preferences",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(Constants.SharedPreferenceConstants.SOS_NUMBER_1, numbers[0])
            putString(Constants.SharedPreferenceConstants.SOS_NUMBER_2, numbers[1])
            apply()
        }
    }

}