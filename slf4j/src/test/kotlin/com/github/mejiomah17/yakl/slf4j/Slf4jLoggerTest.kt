package com.github.mejiomah17.yakl.slf4j

import com.github.mejiomah17.yakl.api.LogLevel
import com.github.mejiomah17.yakl.api.Logger
import com.github.mejiomah17.yakl.core.LogAppender
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import com.github.mejiomah17.yakl.core.SimpleLoggerFather
import com.github.mejiomah17.yakl.core.sync.SyncMainLogger
import com.github.mejiomah17.yakl.dsl.logging
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.slf4j.MDC
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.slf4j.impl.StaticLoggerBinder
import java.time.Instant

class Slf4jLoggerTest {
    init {
        StaticLoggerBinder.instance = StaticLoggerBinder<SimpleLoggerFather>(
            logging {
            }
        )
    }

    val mainLogger = SyncMainLogger(emptyList())

    @Test
    fun `returns name from yakl`() {
        Slf4jLogger(
            object : Logger {
                override val name: String = "test"

                override fun log(
                    level: LogLevel,
                    throwable: Throwable?,
                    time: Instant,
                    messageContext: Map<String, out Any>,
                    contentSupplier: () -> Any
                ) {
                    TODO("Not yet implemented")
                }
            },
            mainLogger
        ).name shouldBe "test"
    }

    @Test
    fun `extract exception to yakl`() {
        var th: Throwable? = null
        val exception = IllegalStateException()
        Slf4jLogger(
            object : Logger {
                override val name: String = "test"

                override fun log(
                    level: LogLevel,
                    throwable: Throwable?,
                    time: Instant,
                    messageContext: Map<String, out Any>,
                    contentSupplier: () -> Any
                ) {
                    th = throwable
                }
            },
            mainLogger
        ).error("", "", exception)
        th.shouldBeSameInstanceAs(exception)
    }

    @TestFactory
    fun shouldLog(): List<DynamicTest> {
        return listOf(
            LogLevel.TRACE,
            LogLevel.DEBUG,
            LogLevel.INFO,
            LogLevel.WARN,
            LogLevel.ERROR
        ).flatMap { level ->
            listOf(
                DynamicTest.dynamicTest("$level should log message") {
                    singleMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log format with 1 arg") {
                    formatWithOneArgMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log format with 2 arg") {
                    formatWithTwoArgMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log format with arg array") {
                    formatWithArgArrayMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log message with throwable") {
                    messageAndThrowableMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log message with marker") {
                    messageAndMarkerMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log format with 1 arg and marker") {
                    formatWithOneArgAndMarkerMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log format with 2 arg and marker") {
                    formatWithTwoArgAndMarkerMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log format with arg array and marker") {
                    formatWithArgArrayAndMarkerMessageTest(level)
                },
                DynamicTest.dynamicTest("$level should log marker message and throwable") {
                    markerMessageAndThrowableMessageTest(level)
                },
            )
        }
    }

    @TestFactory
    fun shouldAskMainLoggerForLoggerEnabled(): List<DynamicTest> {
        return listOf(
            LogLevel.TRACE,
            LogLevel.DEBUG,
            LogLevel.INFO,
            LogLevel.WARN,
            LogLevel.ERROR
        ).flatMap { level ->
            listOf(
                DynamicTest.dynamicTest("is${level}enabled() should use filters") {
                    noParamEnabledTest(level)
                },
                DynamicTest.dynamicTest("is${level}enabled(Marker) should log marker message and throwable") {
                    markerEnabledTest(level)
                },
            )
        }
    }

    private fun noParamEnabledTest(
        level: LogLevel
    ) {
        val method: Slf4jLogger.() -> Boolean = when (level) {
            LogLevel.TRACE -> Slf4jLogger::isTraceEnabled
            LogLevel.DEBUG -> Slf4jLogger::isDebugEnabled
            LogLevel.INFO -> Slf4jLogger::isInfoEnabled
            LogLevel.WARN -> Slf4jLogger::isWarnEnabled
            LogLevel.ERROR -> Slf4jLogger::isErrorEnabled
            LogLevel.FATAL -> TODO()
        }
        val denyAppender = object : LogAppender {
            override val name: String = "deny"
            override val filter: LogFilter = LogFilter {
                it.loggerName shouldBe "testLogger"
                LogFilter.Result.DENY
            }

            override fun append(logMessage: LogMessage) {
                TODO("Not yet implemented")
            }
        }
        val allowAppender = object : LogAppender {
            override val name: String = "allow"
            override val filter: LogFilter = LogFilter {
                it.loggerName shouldBe "testLogger"
                LogFilter.Result.ALLOW
            }

            override fun append(logMessage: LogMessage) {
                TODO("Not yet implemented")
            }
        }
        val logger = mockk<Logger>() {
            every {
                name
            } returns "testLogger"
        }
        Slf4jLogger(log = logger, SyncMainLogger(listOf(denyAppender))).method() shouldBe false
        Slf4jLogger(log = logger, SyncMainLogger(listOf(denyAppender, allowAppender))).method() shouldBe true
    }

    private fun markerEnabledTest(
        level: LogLevel
    ) {
        val method: Slf4jLogger.(Marker) -> Boolean = when (level) {
            LogLevel.TRACE -> Slf4jLogger::isTraceEnabled
            LogLevel.DEBUG -> Slf4jLogger::isDebugEnabled
            LogLevel.INFO -> Slf4jLogger::isInfoEnabled
            LogLevel.WARN -> Slf4jLogger::isWarnEnabled
            LogLevel.ERROR -> Slf4jLogger::isErrorEnabled
            LogLevel.FATAL -> TODO()
        }
        val denyAppender = object : LogAppender {
            override val name: String = "deny"
            override val filter: LogFilter = LogFilter {
                it.loggerName shouldBe "testLogger"
                it.messageContext["sl4jMarkers"].shouldBe(listOf("test"))
                LogFilter.Result.DENY
            }

            override fun append(logMessage: LogMessage) {
                TODO("Not yet implemented")
            }
        }
        val allowAppender = object : LogAppender {
            override val name: String = "allow"
            override val filter: LogFilter = LogFilter {
                it.loggerName shouldBe "testLogger"
                it.messageContext["sl4jMarkers"].shouldBe(listOf("test"))
                LogFilter.Result.ALLOW
            }

            override fun append(logMessage: LogMessage) {
                TODO("Not yet implemented")
            }
        }
        val logger = mockk<Logger>() {
            every {
                name
            } returns "testLogger"
        }
        val marker = MarkerFactory.getMarker("test")
        Slf4jLogger(log = logger, SyncMainLogger(listOf(denyAppender))).method(marker) shouldBe false
        Slf4jLogger(log = logger, SyncMainLogger(listOf(denyAppender, allowAppender))).method(marker) shouldBe true
    }

    private fun singleMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(String) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        log.method("test")
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = null,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test"
    }

    private fun formatWithOneArgMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(String, Any?) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        log.method("test {}", "3")
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = null,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test 3"
    }

    private fun formatWithTwoArgMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(String, Any?, Any?) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        log.method("test {} {}", "3", "4")
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = null,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test 3 4"
    }

    private fun formatWithArgArrayMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(String, Array<Any?>) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        log.method("test {} {}", arrayOf("3", "4"))
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = null,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test 3 4"
    }

    private fun formatWithOneArgAndMarkerMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(Marker, String, Any?) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        log.method(MarkerFactory.getMarker("hey"), "test {}", "3")
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = null,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                    "sl4jMarkers" to listOf("hey")
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test 3"
    }

    private fun formatWithTwoArgAndMarkerMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(Marker, String, Any?, Any?) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        log.method(MarkerFactory.getMarker("hey"), "test {} {}", "3", "4")
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = null,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                    "sl4jMarkers" to listOf("hey")
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test 3 4"
    }

    private fun formatWithArgArrayAndMarkerMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(Marker, String, Array<Any?>) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        log.method(MarkerFactory.getMarker("hey"), "test {} {}", arrayOf("3", "4"))
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = null,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                    "sl4jMarkers" to listOf("hey")
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test 3 4"
    }

    private fun messageAndThrowableMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(String, Throwable) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        val exception = IllegalArgumentException()
        log.method("test", exception)
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = exception,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test"
    }

    private fun messageAndMarkerMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(Marker, String) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        log.method(MarkerFactory.getMarker("hey"), "test")
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = null,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                    "sl4jMarkers" to listOf("hey")
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test"
    }

    private fun markerMessageAndThrowableMessageTest(
        level: LogLevel
    ) {
        val yaklMock = mockk<Logger>(relaxed = true)
        val log = Slf4jLogger(yaklMock, mainLogger)
        MDC.put("one", "two")
        MDC.put("three", "four")
        val method: Slf4jLogger.(Marker, String, Throwable) -> Unit = when (level) {
            LogLevel.TRACE -> Slf4jLogger::trace
            LogLevel.DEBUG -> Slf4jLogger::debug
            LogLevel.INFO -> Slf4jLogger::info
            LogLevel.WARN -> Slf4jLogger::warn
            LogLevel.ERROR -> Slf4jLogger::error
            LogLevel.FATAL -> TODO()
        }
        val exception = IllegalArgumentException()
        log.method(MarkerFactory.getMarker("hey"), "test", exception)
        val slot = slot<() -> Any>()
        verify(exactly = 1) {
            yaklMock.log(
                level = level,
                throwable = exception,
                time = any(),
                messageContext = mapOf(
                    "one" to "two",
                    "three" to "four",
                    "sl4jMarkers" to listOf("hey")
                ),
                contentSupplier = capture(slot)
            )
        }
        slot.captured.invoke() shouldBe "test"
    }
}
