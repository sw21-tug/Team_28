package com.team28.thehiker

import android.telephony.SmsManager
import java.util.function.Consumer

class SMSWrapper(val smsManager: SmsManager, val delayMS: Long, val numbers: List<String>){

    private var thread : Thread
    private var message = "This is a placeholder message: [Chuckles] I'm in danger"
    private var messageFineAgain = "This is a placeholder message: [Chuckles] I'm not in danger anymore"
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
}
