package com.github.mejiomah17.yakl.slf4j

import com.github.mejiomah17.yakl.api.LoggerFather
import com.github.mejiomah17.yakl.core.MainLoggerHolder
import org.slf4j.ILoggerFactory
import org.slf4j.Logger

internal class Slf4jLoggerFactory(
    private val father: LoggerFather,
    private val mainLoggerHolder: MainLoggerHolder,
) : ILoggerFactory {
    override fun getLogger(name: String?): Logger =
        Slf4jLogger(
            log = father.createLogger(name ?: "UNKNOWN"),
            mainLogger = mainLoggerHolder.mainLogger,
        )
}
