package com.github.mejiomah17.yakl.core.filter

import com.github.mejiomah17.yakl.api.LogLevel
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import java.util.concurrent.ConcurrentHashMap

/**
 * Filter based on [LogMessage.loggerName]
 * [LoggerNameFilter] evaluates [LogLevel] for [LogMessage.loggerName] once, and put it into cache.
 * It means that subsequent log operations will spend less time for filtering.
 *
 */
public class LoggerNameFilter private constructor(
    filters: List<Pair<Regex, LogLevel>>
) : LogFilter {
    /**
     * [regexToLevel] has priority as stack. First argument will have less priority than second argument.
     *
     *
     * Example
     * LoggerNameFilter(
     *     Regex(".*") to LogLevel.INFO,
     *     Regex("com.github.mejiomah17.*") to LogLevel.TRACE,
     *     Regex("org.springframework.*") to LogLevel.ERROR,
     * )
     *
     *  Logger(name="org.apache").trace{"hey"} will not be logged because
     *  "org.apache" does not match "org.springframework.*" ,does not match "com.github.mejiomah17.*"
     *  and has lower log level than INFO (for ".*")
     *
     *
     *  Logger(name="org.apache").info{"hey"} and Logger(name="org.apache").warn{"hello"} will be logged because
     *  "org.apache" does not matches "org.springframework.*" ,does not match "com.github.mejiomah17.*",
     *  but matches ".*" and has appropriate log levels
     *
     *
     *  Logger(name="org.springframework.core").warn{"hey"} will not be logged because
     * "org.springframework.core"  match "org.springframework.*" and has lower log level than ERROR (for "org.springframework.*")
     *
     *  Logger(name="com.github.mejiomah17.example").debug{"hey"} will not be logged because
     *  "com.github.mejiomah17.example" does not match "org.springframework.*",
     *  but "com.github.mejiomah17.example"  match "com.github.mejiomah17.*" and has appropriate log level
     *
     *
     */
    public constructor(vararg regexToLevel: Pair<Regex, LogLevel>) : this(regexToLevel.toList())

    private val filters = filters.reversed()
    private val cache = ConcurrentHashMap<String, LogLevel?>()

    override fun isLogEnabled(logMessage: LogMessage): LogFilter.Result {
        val level = cache.computeIfAbsent(logMessage.loggerName) {
            filters.firstOrNull { (regex, _) ->
                regex.matches(logMessage.loggerName)
            }?.second
        } ?: return LogFilter.Result.NEXT

        return if (level <= logMessage.level) {
            LogFilter.Result.ALLOW
        } else {
            LogFilter.Result.DENY
        }
    }
}
