package com.example.snowflakes

import org.junit.Assert.assertTrue
import org.junit.Test

class SnowflakeTest {

    @Test
    fun testSnowflakeMovementAndSlowing() {
        val snowflake = Snowflake(1000, 2000)

        snowflake.y = 100f
        val initialY = snowflake.y

        snowflake.move()
        val moveTopDelta = snowflake.y - initialY

        snowflake.y = 1800f
        val bottomY = snowflake.y

        snowflake.move()
        val moveBottomDelta = snowflake.y - bottomY

        assertTrue("Top movement ($moveTopDelta) should be faster than bottom movement ($moveBottomDelta)", moveTopDelta > moveBottomDelta)
    }
}
