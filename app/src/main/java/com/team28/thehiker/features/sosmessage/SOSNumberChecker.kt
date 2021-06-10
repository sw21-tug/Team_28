package com.team28.thehiker.features.sosmessage

class SOSNumberChecker : ISOSNumberChecker {

    override fun checkSOSNumber(number: String): Boolean {
        return number.matches(Regex("\\+?[0-9]+"))
    }

    override fun checkSOSNumberLength(number: String): Boolean {
        if (number.isEmpty()) return false

        return if(number[0] == '+')
            number.length in 6..16
        else
            number.length in 5..15
    }

}