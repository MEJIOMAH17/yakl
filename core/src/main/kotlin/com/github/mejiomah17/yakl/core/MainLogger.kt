package com.github.mejiomah17.yakl.core

import java.io.Closeable
import java.util.concurrent.ConcurrentMap

public interface MainLogger : Closeable {
    public val appenders: ConcurrentMap<String, LogAppender>
    public fun log(logMessage: LogMessage)

    override fun close() {
        appenders.values.forEach {
            if (it is Closeable) {
                it.close()
            }
        }
    }
}
