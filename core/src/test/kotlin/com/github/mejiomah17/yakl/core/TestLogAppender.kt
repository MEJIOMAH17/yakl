package com.github.mejiomah17.yakl.core

import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

class TestLogAppender(
    override val name: String = UUID.randomUUID().toString(),
    override val filter: LogFilter = LogFilter.allowAll
) : LogAppender {
    val logs = CopyOnWriteArrayList<LogMessage>()
    override fun append(logMessage: LogMessage) {
        logs.add(logMessage)
    }
}
