package com.github.mejiomah17.yakl.stdout.bench

import com.github.mejiomah17.yakl.api.LogLevel
import com.github.mejiomah17.yakl.core.LogFormatter
import com.github.mejiomah17.yakl.core.filter.LoggerNameFilter
import com.github.mejiomah17.yakl.dsl.logging
import com.github.mejiomah17.yakl.stdout.stdout
import org.apache.logging.log4j.LogManager
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.infra.Blackhole
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@State(Scope.Benchmark)
open class StdoutLogBench {
    companion object {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.ms").withZone(ZoneId.from(ZoneOffset.UTC))
        val loggerFather = logging {
            stdout("hey") {
                filter = LoggerNameFilter(
                    Regex(".*") to LogLevel.INFO,
                    Regex("example.*") to LogLevel.DEBUG
                )
                formatter = LogFormatter { builder, msg ->
                    val time = dateFormatter.format(msg.time)
                    builder.append("$time [${msg.level}] ${msg.loggerName} : ${msg.content}")
                }
            }
        }
        val yaklExample = loggerFather.createLogger("example")
        val yaklTest = loggerFather.createLogger("test")
        val logbackExample = LoggerFactory.getLogger("example")
        val logbackTest = LoggerFactory.getLogger("test")
        val log4j2Example = LogManager.getLogger("example")
        val log4j2Test = LogManager.getLogger("test")
    }

    @Benchmark
    open fun yakl(bh: Blackhole) {
        yaklExample.debug { "hey" }
        yaklTest.debug { "hey" }
    }

    @Benchmark
    open fun logback(bh: Blackhole) {
        logbackExample.debug("hey")
        logbackTest.debug("hey")
    }

    @Benchmark
    open fun log4j2(bh: Blackhole) {
        log4j2Example.debug("hey")
        log4j2Test.debug("hey")
    }
}