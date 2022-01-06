package com.github.mejiomah17.yakl.dsl

import com.github.mejiomah17.yakl.core.LogAppender
import com.github.mejiomah17.yakl.core.SimpleLoggerFather
import com.github.mejiomah17.yakl.core.TestLogAppender
import com.github.mejiomah17.yakl.core.sync.SyncMainLogger
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.jupiter.api.Test

class LoggingKtTest {
    @Test
    fun `should create log father with main logger from supplier and pass appenders`() {
        var appenderSlot: Collection<LogAppender> = emptyList()
        val appender = TestLogAppender()
        val logger = SyncMainLogger(emptyList())

        val father = logging {
            mainLoggerSupplier = {
                appenderSlot = it
                logger
            }
            appenders.add(appender)
        }
        father.shouldBeInstanceOf<SimpleLoggerFather>()
        father.mainLogger shouldBeSameInstanceAs logger
        appenderSlot shouldBe arrayListOf(appender)
    }
}
