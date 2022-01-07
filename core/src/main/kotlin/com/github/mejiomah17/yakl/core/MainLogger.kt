package com.github.mejiomah17.yakl.core

import java.util.concurrent.ConcurrentMap

public interface MainLogger {
    public val appenders: ConcurrentMap<String, LogAppender>

    public fun log(logMessage: LogMessage)
}
