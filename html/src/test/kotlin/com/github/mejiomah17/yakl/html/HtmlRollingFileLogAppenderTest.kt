package com.github.mejiomah17.yakl.html

import com.github.mejiomah17.yakl.dsl.logging
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File
import java.time.Instant
import java.util.UUID

class HtmlRollingFileLogAppenderTest {
    @Test
    fun appends_html_formatted_file() {
        val file = File(UUID.randomUUID().toString())
        val time = Instant.parse("2022-04-14T15:45:33.110134Z")
        try {
            logging {
                html("html") {
                    fileCreator = { file }

                }
            }.use {
                val logger = it.createLogger("test")
                logger.info(time = time, messageContext = mapOf("x" to "y")) { "test" }
                logger.info(time = time) { "test1" }
                logger.info(time = time) { "test2" }
                logger.info(time = time) { "test3" }
            }
            file.readText() shouldBe this.javaClass.classLoader.getResourceAsStream("test.html").reader().readText()
        } finally {
            file.delete()
        }
    }
}