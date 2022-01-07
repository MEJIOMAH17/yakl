package com.github.mejiomah17.yakl.slf4j

import com.github.mejiomah17.yakl.api.LoggerFather
import com.github.mejiomah17.yakl.core.MainLoggerHolder
import org.slf4j.ILoggerFactory
import org.slf4j.Logger

internal class Slf4jLoggerFactory<T>(
    private val loggerFather: T
) : ILoggerFactory where T : LoggerFather, T : MainLoggerHolder {
    override fun getLogger(name: String?): Logger {
        return Slf4jLogger(
            log = loggerFather.createLogger(name ?: "UNKNOWN"),
            mainLogger = loggerFather.mainLogger
        )
    }
}
