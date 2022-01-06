package com.github.mejiomah17.yakl.core

public interface LogAppender {
    public val name: String
    public val filter: LogFilter
    public fun append(logMessage: LogMessage)
}
