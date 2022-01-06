package com.github.mejiomah17.yakl.core.async

import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import com.github.mejiomah17.yakl.core.TestLogAppender
import com.github.mejiomah17.yakl.core.invoke
import com.github.mejiomah17.yakl.core.sync.SyncMainLogger
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicBoolean

class SyncMainLoggerTest {
    @Test
    fun `should call all appenders`() {
        val first = TestLogAppender()
        val second = TestLogAppender()
        val logger = SyncMainLogger(
            listOf(first, second)
        )
        val logMessage = LogMessage()
        logger.log(logMessage)
        first.logs.firstOrNull() shouldBeSameInstanceAs logMessage
        second.logs.firstOrNull() shouldBeSameInstanceAs logMessage
    }

    @Test
    fun `should not call appender if filter deny append`() {
        val first = TestLogAppender()
        val filterCalled = AtomicBoolean(false)
        val second = TestLogAppender(
            filter = LogFilter {
                filterCalled.set(true)
                LogFilter.Result.DENY
            }
        )
        val logger = SyncMainLogger(
            listOf(first, second)
        )
        val logMessage = LogMessage()
        logger.log(logMessage)
        first.logs.firstOrNull() shouldBeSameInstanceAs logMessage
        filterCalled.get() shouldBe true
        second.logs.shouldBeEmpty()
    }
}
