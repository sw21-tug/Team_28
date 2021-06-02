package com.team28.thehiker.features.sosmessage

import android.app.Activity
import android.location.Location
import android.telephony.SmsManager
import com.team28.thehiker.R
import com.team28.thehiker.location.HikerLocationCallback
import java.lang.StringBuilder
import java.util.*

class SMSWrapper(val smsManager: SmsManager, val delayMS: Long, val numbers: List<String>, val context: Activity) : HikerLocationCallback {

    private var thread : Thread
    private lateinit var location : Location

    private var stopAlarm = false
    private var threadRunning = false

    init {
        thread = Thread(Runnable {
            var prevLocation : Location? = null
            while (!stopAlarm) {
                if(prevLocation == null || prevLocation != location){
                    numbers.forEach {
                        smsManager.sendTextMessage(it, null, getGoogleMapsLocationMessage(true), null, null)
                    }
                    prevLocation = location
                }
                Thread.sleep(delayMS)
            }
        })
    }

    fun stop() {
        stopAlarm = true
        numbers.forEach {
            smsManager.sendTextMessage(it, null, getGoogleMapsLocationMessage(), null, null)
        }
    }

    override fun notifyLocationUpdate(location: Location) {
        this.location = location
        if (!threadRunning) {
            thread.start()
            threadRunning = true
        }
    }

    fun getGoogleMapsLocationMessage(isEmergencyStart : Boolean = false) : String {
        val stringBuilder = StringBuilder()
        val calendar = Calendar.getInstance()

        if (isEmergencyStart)
            stringBuilder.appendLine(context.getString(R.string.sms_message_sos))
        else
            stringBuilder.appendLine(context.getString(R.string.sms_message_emergency_over))

        stringBuilder.append("https://www.google.com/maps/search/?api=1&query=").append(location.latitude).append(",").append(location.longitude)
                .append("  ")
                .append("(").append(calendar.get(Calendar.HOUR_OF_DAY)).append(":").append(calendar.get(Calendar.MINUTE)).append(")")

        return stringBuilder.toString()
    }
}
