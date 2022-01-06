package com.github.mejiomah17.yakl.core

import com.github.mejiomah17.yakl.api.LogLevel
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException
import java.time.Instant

class SimpleLoggerFatherTest {
    @Test
    fun `should create logger which delegate to main logger`() {
        val mainLogger = TestMainLogger()
        val logFather = SimpleLoggerFather(mainLogger)
        val logger = logFather.createLogger("test")
        val throwable = IllegalArgumentException()
        val time = Instant.now()
        val context = mapOf("s" to Any())
        val supplier = { time }
        logger.log(
            level = LogLevel.INFO,
            throwable = throwable,
            time = time,
            messageContext = context,
            contentSupplier = supplier
        )
        mainLogger.messages.shouldNotBeEmpty()
        val message = mainLogger.messages.first()
        message.loggerName shouldBe "test"
        message.throwable shouldBeSameInstanceAs throwable
        message.time shouldBeSameInstanceAs time
        message.messageContext shouldBeSameInstanceAs context
        message.contentSupplier shouldBeSameInstanceAs supplier
    }
}
