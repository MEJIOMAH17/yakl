package com.github.mejiomah17.yakl.core

import com.github.mejiomah17.yakl.api.LogLevel
import java.time.Instant

public class LogMessage(
    public val contentSupplier: () -> Any,
    public val time: Instant,
    public val throwable: Throwable?,
    public val level: LogLevel,
    public val loggerName: String,
    public val messageContext: Map<String, Any>,
) {
    public val content: Any by lazy(mode = LazyThreadSafetyMode.NONE) {
        contentSupplier()
    }

    public companion object {
    }
}
