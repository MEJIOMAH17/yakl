package com.github.mejiomah17.yakl.core.async

import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import com.github.mejiomah17.yakl.core.TestLogAppender
import com.github.mejiomah17.yakl.core.invoke
import io.kotest.matchers.collections.shouldBeEmpty
import org.awaitility.kotlin.await
import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class AsyncMainLoggerTest {
    @Test
    fun `should call all appenders`() {
        val first = TestLogAppender()
        val second = TestLogAppender()
        val logger = AsyncMainLogger(
            Executors.newSingleThreadExecutor(),
            listOf(first, second)
        )
        val logMessage = LogMessage()
        logger.log(logMessage)
        await.atMost(Duration.ofMillis(500)).pollInterval(Duration.ofMillis(1)).until {
            first.logs.firstOrNull() === logMessage && second.logs.firstOrNull() === logMessage
        }
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
        val logger = AsyncMainLogger(
            Executors.newSingleThreadExecutor(),
            listOf(first, second)
        )
        val logMessage = LogMessage()
        logger.log(logMessage)
        await.atMost(Duration.ofMillis(500)).pollInterval(Duration.ofMillis(1)).until {
            first.logs.firstOrNull() === logMessage && filterCalled.get()
        }
        second.logs.shouldBeEmpty()
    }
}
