package com.team28.thehiker.features.sosmessage

interface ISOSNumberChecker {

    fun checkSOSNumber(number: String): Boolean

    fun checkSOSNumberLength(number: String): Boolean

}