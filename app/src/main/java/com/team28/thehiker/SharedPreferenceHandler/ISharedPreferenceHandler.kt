package com.team28.thehiker.SharedPreferenceHandler

import android.app.Activity
import android.content.Context
import java.time.LocalDate

interface ISharedPreferenceHandler {

    fun getLocalizationString(context: Activity) : String?

    fun setLocalizationString(context: Activity, localizationString: String)

    fun getSavedStepCount(context: Activity) : Int

    fun setSavedStepCount(context: Activity, numberOfSteps : Int)

    fun getLastStepCountUpdate(context: Activity) : String?

    fun setLastStepCountUpdate(context: Activity, lastStepUpdate: String)

    fun setStepCountHistory(context: Activity, history: String)

    fun getStepCountHistory(context: Activity) : String?

}