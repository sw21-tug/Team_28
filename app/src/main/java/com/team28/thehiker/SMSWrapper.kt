package com.team28.thehiker

import android.telephony.SmsManager

class SMSWrapper(val smsManager: SmsManager, val delayMS: Long, numbers: List<String>){

    private var thread : Thread
    private var message = "This is a placeholder message: [Chuckles] I'm in danger"
    private var messageFineAgain = "This is a placeholder message: [Chuckles] I'm not in danger anymore"
    private var stopAlarm = false

    init {
        thread = Thread(Runnable {
            while (!stopAlarm) {
                smsManager.sendTextMessage("+436643556189", null, message, null, null)
                Thread.sleep(delayMS)
            }
        })
    }

    fun start() {
        thread.start()
    }

    fun stop() {
        stopAlarm = true
        smsManager.sendTextMessage("+436643556189", null, messageFineAgain, null, null)
    }
}
