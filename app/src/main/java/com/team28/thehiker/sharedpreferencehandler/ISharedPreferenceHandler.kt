package com.team28.thehiker.sharedpreferencehandler

import android.app.Activity

interface ISharedPreferenceHandler {

    fun getLocalizationString(context: Activity) : String?

    fun setLocalizationString(context: Activity, localizationString: String)

    fun getSavedStepCount(context: Activity) : Int

    fun setSavedStepCount(context: Activity, numberOfSteps : Int)

    fun getLastStepCountUpdate(context: Activity) : String?

    fun setLastStepCountUpdate(context: Activity, lastStepUpdate: String)

}