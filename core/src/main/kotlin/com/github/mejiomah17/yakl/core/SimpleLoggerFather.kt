package com.github.mejiomah17.yakl.core

import com.github.mejiomah17.yakl.api.Logger
import com.github.mejiomah17.yakl.api.LoggerFather
import java.io.Closeable
import java.util.concurrent.ConcurrentHashMap

public open class SimpleLoggerFather internal constructor(
    public override val mainLogger: MainLogger
) : LoggerFather, MainLoggerHolder, Closeable {
    private val loggers = ConcurrentHashMap<String, Logger>()

    override fun createLogger(name: String): Logger {
        return loggers.computeIfAbsent(name) {
            SimpleLogger(name, mainLogger)
        }
    }

    override fun close() {
        mainLogger.close()
    }
}
