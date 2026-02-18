package com.github.mejiomah17.yakl.core

import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.jupiter.api.Test

class LogMessageTest {
    @Test
    fun `content should return from supplier`() {
        val value = Any()
        LogMessage(contentSupplier = { value }).content shouldBeSameInstanceAs value
    }
}
