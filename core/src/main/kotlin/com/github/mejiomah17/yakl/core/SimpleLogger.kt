package com.github.mejiomah17.yakl.core

import com.github.mejiomah17.yakl.api.LogLevel
import com.github.mejiomah17.yakl.api.Logger
import java.time.Instant

public open class SimpleLogger(
    override val name: String,
    protected val mainLogger: MainLogger
) : Logger {

    override fun log(
        level: LogLevel,
        throwable: Throwable?,
        time: Instant,
        messageContext: Map<String, Any>,
        contentSupplier: () -> Any
    ) {
        mainLogger.log(
            LogMessage(
                contentSupplier = contentSupplier,
                time = time,
                throwable = throwable,
                level = level,
                loggerName = name,
                messageContext = messageContext
            )
        )
    }
}
