package com.github.mejiomah17.yakl.core

import java.util.concurrent.ConcurrentHashMap

public interface MainLogger {
    public val appenders: ConcurrentHashMap<String, LogAppender>

    public fun log(logMessage: LogMessage)
}
