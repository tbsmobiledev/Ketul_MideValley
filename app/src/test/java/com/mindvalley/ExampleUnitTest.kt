package com.mindvalley

import com.mindvalley.ui.LogHistory
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest() {

    private var mLogHistory: LogHistory? = null

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Before
    fun createLogHistory() {
        mLogHistory = LogHistory()
    }

    @Test //This is executed before the @Test executes
    fun testAPICall() {
        println("Ready for testing")
        mLogHistory?.apiCallTest()
    }

    @Test //This is executed after the @Test executes
    fun dataBind() {
        mLogHistory?.dataBinding()
    }

}