package com.github.mejiomah17.yakl.core

import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test

class DefaultLogFormatterTest {
    @Test
    fun `appends throwable`() {
        val builder = StringBuilder()
        LogFormatter.defaultFormatter.format(builder, LogMessage(throwable = IllegalStateException()))
        builder.toString() shouldContain "IllegalStateException"
    }
}