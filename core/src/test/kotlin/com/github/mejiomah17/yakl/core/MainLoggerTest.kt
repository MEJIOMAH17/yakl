package com.github.mejiomah17.yakl.core

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.Closeable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class MainLoggerTest {
    @Test
    fun `closes all appenders`() {
        val logger = object : MainLogger {
            override val appenders: ConcurrentMap<String, LogAppender> = ConcurrentHashMap()
            override fun log(logMessage: LogMessage) {
                // do nothing
            }
        }

        logger.appenders["notClosable"] = object : LogAppender {
            override val name: String = "notClosable"
            override val filter: LogFilter = LogFilter.allowAll
            override fun append(logMessage: LogMessage) {
                TODO("Not yet implemented")
            }
        }
        val first = ClosableLogAppender("closeable")
        val second = ClosableLogAppender("closeable2")
        logger.appenders["closeable"] = first
        logger.appenders["closeable2"] = second
        logger.close()
        first.wasClosed shouldBe true
        second.wasClosed shouldBe true
    }

    private class ClosableLogAppender(
        override val name: String
    ) : LogAppender, Closeable {
        override val filter: LogFilter = LogFilter.allowAll
        var wasClosed = false

        override fun append(logMessage: LogMessage) {
            // do nothing
        }

        override fun close() {
            wasClosed = true
        }
    }
}
