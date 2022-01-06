package com.github.mejiomah17.yakl.core

import java.util.concurrent.ConcurrentHashMap

class TestMainLogger : MainLogger {
    override val appenders = ConcurrentHashMap<String, LogAppender>()
    val messages = arrayListOf<LogMessage>()
    override fun log(logMessage: LogMessage) {
        messages.add(logMessage)
    }
}
