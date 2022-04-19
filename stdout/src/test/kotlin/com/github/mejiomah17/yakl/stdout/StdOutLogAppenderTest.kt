package com.github.mejiomah17.yakl.stdout

import com.github.mejiomah17.yakl.api.LogLevel
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.jupiter.api.Test
import java.io.PrintStream
import java.time.Instant

class StdOutLogAppenderTest {
    @Test
    fun shouldAppendToStdOut() {
        lateinit var messageSlot: LogMessage
        val message = LogMessage(
            contentSupplier = {
            },
            time = Instant.now(),
            throwable = null,
            level = LogLevel.INFO,
            loggerName = "123",
            messageContext = emptyMap()
        )
        val out: PrintStream = System.out
        try {
            val messages = arrayListOf<String>()
            val newStream = object : PrintStream(out) {
                override fun print(s: String) {
                    messages.add(s)
                }
            }
            System.setOut(newStream)
            val appender = StdOutLogAppender(
                "test", filter = LogFilter.allowAll,
                formatter = { builder, msg ->
                    messageSlot = msg
                    builder.append("bla bla bla")
                }
            )
            appender.filter shouldBe LogFilter.allowAll
            appender.append(message)
            messageSlot shouldBeSameInstanceAs message
            messages.first() shouldBe "bla bla bla\n"
        } finally {
            System.setOut(out)
        }
    }
}
