package com.team28.thehiker

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FindMeTest {


    @Test
    fun setMapPositionWithoutMap()
    {
        val test = FindMeActivity()
        var return_value = test.setMapPosition(0.0, 0.0)
        assert(return_value == -1)
    }
}