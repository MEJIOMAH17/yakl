package com.github.mejiomah17.yakl.core

import com.github.mejiomah17.yakl.api.LogLevel
import java.time.Instant

operator fun LogMessage.Companion.invoke(
    level: LogLevel = LogLevel.INFO,
    loggerName: String = "",
    contentSupplier: () -> Any = {}
): LogMessage {
    return LogMessage(
        time = Instant.now(),
        level = level,
        throwable = null,
        contentSupplier = contentSupplier,
        loggerName = loggerName,
        messageContext = emptyMap()
    )
}
