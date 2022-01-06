package com.github.mejiomah17.yakl.core.filter

import com.github.mejiomah17.yakl.api.LogLevel
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import com.github.mejiomah17.yakl.core.invoke
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class LoggerNameFilterTest {
    companion object {
        private val filter = LoggerNameFilter(
            Regex(".*") to LogLevel.INFO,
            Regex("com.github.mejiomah17.*") to LogLevel.TRACE,
            Regex("org.springframework.*") to LogLevel.ERROR,
        )
    }

    @Test
    fun `should not log apache trace`() {
        filter.isLogEnabled(
            LogMessage(
                LogLevel.TRACE,
                "org.apache"
            )
        ) shouldBe LogFilter.Result.DENY
    }

    @Test
    fun `should log apache info`() {
        filter.isLogEnabled(
            LogMessage(
                LogLevel.INFO,
                "org.apache"
            )
        ) shouldBe LogFilter.Result.ALLOW
    }

    @Test
    fun `should not log spring warn`() {
        filter.isLogEnabled(
            LogMessage(
                LogLevel.WARN,
                "org.springframework.core"
            )
        ) shouldBe LogFilter.Result.DENY
    }

    @Test
    fun `should log example debug`() {
        filter.isLogEnabled(
            LogMessage(
                LogLevel.DEBUG,
                "com.github.mejiomah17.example"
            )
        ) shouldBe LogFilter.Result.ALLOW
    }
}
