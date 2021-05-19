package com.team28.thehiker

import android.location.Location
import android.telephony.SmsManager

class SMSWrapper(val smsManager: SmsManager, val delayMS: Long, val numbers: List<String>) : HikerLocationCallback{

    private var thread : Thread
    private val message = "This is a placeholder message: [Chuckles] I'm in danger"
    private val messageFineAgain = "This is a placeholder message: [Chuckles] I'm not in danger anymore"
    private lateinit var location : Location

    private var stopAlarm = false
    private var threadRunning = false

    init {
        thread = Thread(Runnable {
            while (!stopAlarm) {
                numbers.forEach {
                    smsManager.sendTextMessage(it, null, "$message $location", null, null)
                }
                Thread.sleep(delayMS)
            }
        })
    }

    fun stop() {
        stopAlarm = true
        numbers.forEach {
            smsManager.sendTextMessage(it, null, messageFineAgain, null, null)
        }
    }

    override fun notifyLocationUpdate(location: Location) {
        this.location = location
        if (!threadRunning) {
            thread.start()
            threadRunning = true
        }
    }

    fun getMessage() : String{
        return message
    }
}
