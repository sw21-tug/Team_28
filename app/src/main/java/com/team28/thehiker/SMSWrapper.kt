package com.team28.thehiker

import android.location.Location
import android.telephony.SmsManager

class SMSWrapper(val smsManager: SmsManager, val delayMS: Long, val numbers: List<String>) : HikerLocationCallback{

    private var thread : Thread
    private val message = "This is a placeholder message: [Chuckles] I'm in danger"
    private val messageFineAgain = "This is a placeholder message: [Chuckles] I'm not in danger anymore"
    private var stopAlarm = false

    init {
        thread = Thread(Runnable {
            while (!stopAlarm) {
                numbers.forEach {
                    smsManager.sendTextMessage(it, null, message, null, null)
                };
                Thread.sleep(delayMS)
            }
        })
    }

    fun start() {
        thread.start()
    }

    fun stop() {
        stopAlarm = true
        numbers.forEach {
            smsManager.sendTextMessage(it, null, message, null, null)
        };
    }

    override fun notifyLocationUpdate(location: Location) {}

    fun getMessage() : String{
        return message
    }
}
