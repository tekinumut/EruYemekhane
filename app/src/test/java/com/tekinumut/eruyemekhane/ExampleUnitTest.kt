package com.tekinumut.eruyemekhane

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val numbers = listOf<Long>(30, 24, 60, 60, 1000)

        val sum = numbers.reduce { sum, element -> sum * element }
        assertEquals(2592000000, sum)
    }
}