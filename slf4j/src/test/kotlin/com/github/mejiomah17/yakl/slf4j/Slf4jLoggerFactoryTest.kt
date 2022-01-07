package com.github.mejiomah17.yakl.slf4j

import com.github.mejiomah17.yakl.api.LogLevel
import com.github.mejiomah17.yakl.core.LogAppender
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import com.github.mejiomah17.yakl.dsl.logging
import com.github.mejiomah17.yakl.dsl.registerSlf4jAdapter
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class Slf4jLoggerFactoryTest {
    @Test
    fun shouldUseYAKL() {
        val messages = arrayListOf<LogMessage>()
        val appender = object : LogAppender {
            override val name: String = "test"
            override val filter: LogFilter = LogFilter.allowAll

            override fun append(logMessage: LogMessage) {
                messages.add(logMessage)
            }
        }
        logging {
            appenders.add(
                appender
            )
            registerSlf4jAdapter()
        }
        LoggerFactory.getLogger("testLogger").info("hey")
        val message = messages.firstOrNull()
        message shouldNotBe null
        message!!.loggerName shouldBe "testLogger"
        message.level shouldBe LogLevel.INFO
        message.content shouldBe "hey"
    }
}
