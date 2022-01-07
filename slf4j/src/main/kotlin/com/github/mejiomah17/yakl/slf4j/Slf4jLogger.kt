package com.github.mejiomah17.yakl.slf4j

import com.github.mejiomah17.yakl.api.LogLevel
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import com.github.mejiomah17.yakl.core.MainLogger
import org.slf4j.Logger
import org.slf4j.MDC
import org.slf4j.Marker
import java.time.Instant

internal class Slf4jLogger(
    private val log: com.github.mejiomah17.yakl.api.Logger,
    private val mainLogger: MainLogger,
) : Logger {
    override fun getName(): String {
        return log.name
    }

    override fun isTraceEnabled(): Boolean {
        return isLogEnabled(LogLevel.TRACE, marker = null)
    }

    override fun isTraceEnabled(marker: Marker?): Boolean {
        return isLogEnabled(LogLevel.TRACE, marker)
    }

    override fun trace(msg: String?) {
        log(
            level = LogLevel.TRACE,
            throwable = null,
            marker = null,
            message = msg
        )
    }

    override fun trace(format: String?, arg: Any?) {
        log(
            level = LogLevel.TRACE,
            throwable = null,
            marker = null,
            format = format,
            arg
        )
    }

    override fun trace(format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.TRACE,
            throwable = null,
            marker = null,
            format = format,
            arg1,
            arg2
        )
    }

    override fun trace(format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.TRACE,
            throwable = null,
            marker = null,
            format = format,
            *arguments
        )
    }

    override fun trace(msg: String?, t: Throwable?) {
        log(
            level = LogLevel.TRACE,
            throwable = t,
            marker = null,
            message = msg
        )
    }

    override fun trace(marker: Marker?, msg: String?) {
        log(
            level = LogLevel.TRACE,
            throwable = null,
            marker = marker,
            message = msg
        )
    }

    override fun trace(marker: Marker?, format: String?, arg: Any?) {
        log(
            level = LogLevel.TRACE,
            throwable = null,
            marker = marker,
            format = format,
            arg
        )
    }

    override fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.TRACE,
            throwable = null,
            marker = marker,
            format = format,
            arg1,
            arg2
        )
    }

    override fun trace(marker: Marker?, format: String?, vararg argArray: Any?) {
        log(
            level = LogLevel.TRACE,
            throwable = null,
            marker = marker,
            format = format,
            *argArray
        )
    }

    override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
        log(
            level = LogLevel.TRACE,
            throwable = t,
            marker = marker,
            message = msg,
        )
    }

    override fun isDebugEnabled(): Boolean {
        return isLogEnabled(LogLevel.DEBUG, null)
    }

    override fun isDebugEnabled(marker: Marker?): Boolean {
        return isLogEnabled(LogLevel.TRACE, marker)
    }

    override fun debug(msg: String?) {
        log(
            level = LogLevel.DEBUG,
            throwable = null,
            marker = null,
            message = msg,
        )
    }

    override fun debug(format: String?, arg: Any?) {
        log(
            level = LogLevel.DEBUG,
            throwable = null,
            marker = null,
            format = format,
            arg
        )
    }

    override fun debug(format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.DEBUG,
            throwable = null,
            marker = null,
            format = format,
            arg1,
            arg2
        )
    }

    override fun debug(format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.DEBUG,
            throwable = null,
            marker = null,
            format = format,
            *arguments
        )
    }

    override fun debug(msg: String?, t: Throwable?) {
        log(
            level = LogLevel.DEBUG,
            throwable = t,
            marker = null,
            message = msg,
        )
    }

    override fun debug(marker: Marker?, msg: String?) {
        log(
            level = LogLevel.DEBUG,
            throwable = null,
            marker = marker,
            message = msg
        )
    }

    override fun debug(marker: Marker?, format: String?, arg: Any?) {
        log(
            level = LogLevel.DEBUG,
            throwable = null,
            marker = marker,
            format = format,
            arg
        )
    }

    override fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.DEBUG,
            throwable = null,
            marker = marker,
            format = format,
            arg1,
            arg2
        )
    }

    override fun debug(marker: Marker?, format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.DEBUG,
            throwable = null,
            marker = marker,
            format = format,
            *arguments
        )
    }

    override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
        log(
            level = LogLevel.DEBUG,
            throwable = t,
            marker = marker,
            message = msg
        )
    }

    override fun isInfoEnabled(): Boolean {
        return isLogEnabled(LogLevel.INFO, null)
    }

    override fun isInfoEnabled(marker: Marker?): Boolean {
        return isLogEnabled(LogLevel.INFO, marker)
    }

    override fun info(msg: String?) {
        log(
            level = LogLevel.INFO,
            throwable = null,
            marker = null,
            message = msg
        )
    }

    override fun info(format: String?, arg: Any?) {
        log(
            level = LogLevel.INFO,
            throwable = null,
            marker = null,
            format = format,
            arg
        )
    }

    override fun info(format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.INFO,
            throwable = null,
            marker = null,
            format = format,
            arg1,
            arg2
        )
    }

    override fun info(format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.INFO,
            throwable = null,
            marker = null,
            format = format,
            *arguments
        )
    }

    override fun info(msg: String?, t: Throwable?) {
        log(
            level = LogLevel.INFO,
            throwable = t,
            marker = null,
            message = msg
        )
    }

    override fun info(marker: Marker?, msg: String?) {
        log(
            level = LogLevel.INFO,
            throwable = null,
            marker = marker,
            message = msg
        )
    }

    override fun info(marker: Marker?, format: String?, arg: Any?) {
        log(
            level = LogLevel.INFO,
            throwable = null,
            marker = marker,
            format = format,
            arg
        )
    }

    override fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.INFO,
            throwable = null,
            marker = marker,
            format = format,
            arg1,
            arg2
        )
    }

    override fun info(marker: Marker?, format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.INFO,
            throwable = null,
            marker = marker,
            format = format,
            *arguments
        )
    }

    override fun info(marker: Marker?, msg: String?, t: Throwable?) {
        log(
            level = LogLevel.INFO,
            throwable = t,
            marker = marker,
            message = msg
        )
    }

    override fun isWarnEnabled(): Boolean {
        return isLogEnabled(LogLevel.WARN, null)
    }

    override fun isWarnEnabled(marker: Marker?): Boolean {
        return isLogEnabled(LogLevel.WARN, marker)
    }

    override fun warn(msg: String?) {
        log(
            level = LogLevel.WARN,
            throwable = null,
            marker = null,
            message = msg
        )
    }

    override fun warn(format: String?, arg: Any?) {
        log(
            level = LogLevel.WARN,
            throwable = null,
            marker = null,
            format = format,
            arg
        )
    }

    override fun warn(format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.WARN,
            throwable = null,
            marker = null,
            format = format,
            *arguments
        )
    }

    override fun warn(format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.WARN,
            throwable = null,
            marker = null,
            format = format,
            arg1,
            arg2
        )
    }

    override fun warn(msg: String?, t: Throwable?) {
        log(
            level = LogLevel.WARN,
            throwable = t,
            marker = null,
            message = msg,
        )
    }

    override fun warn(marker: Marker?, msg: String?) {
        log(
            level = LogLevel.WARN,
            throwable = null,
            marker = marker,
            message = msg
        )
    }

    override fun warn(marker: Marker?, format: String?, arg: Any?) {
        log(
            level = LogLevel.WARN,
            throwable = null,
            marker = marker,
            format = format,
            arg
        )
    }

    override fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.WARN,
            throwable = null,
            marker = marker,
            format = format,
            arg1,
            arg2
        )
    }

    override fun warn(marker: Marker?, format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.WARN,
            throwable = null,
            marker = marker,
            format = format,
            *arguments
        )
    }

    override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
        log(
            level = LogLevel.WARN,
            throwable = t,
            marker = marker,
            message = msg
        )
    }

    override fun isErrorEnabled(): Boolean {
        return isLogEnabled(LogLevel.ERROR, null)
    }

    override fun isErrorEnabled(marker: Marker?): Boolean {
        return isLogEnabled(LogLevel.ERROR, marker)
    }

    override fun error(msg: String?) {
        log(
            level = LogLevel.ERROR,
            throwable = null,
            marker = null,
            message = msg,
        )
    }

    override fun error(format: String?, arg: Any?) {
        log(
            level = LogLevel.ERROR,
            throwable = null,
            marker = null,
            format = format,
            arg
        )
    }

    override fun error(format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.ERROR,
            throwable = null,
            marker = null,
            format = format,
            arg1,
            arg2
        )
    }

    override fun error(format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.ERROR,
            throwable = null,
            marker = null,
            format = format,
            *arguments
        )
    }

    override fun error(msg: String?, t: Throwable?) {
        log(
            level = LogLevel.ERROR,
            throwable = t,
            marker = null,
            message = msg,
        )
    }

    override fun error(marker: Marker?, msg: String?) {
        log(
            level = LogLevel.ERROR,
            throwable = null,
            marker = marker,
            message = msg,
        )
    }

    override fun error(marker: Marker?, format: String?, arg: Any?) {
        log(
            level = LogLevel.ERROR,
            throwable = null,
            marker = marker,
            format = format,
            arg
        )
    }

    override fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        log(
            level = LogLevel.ERROR,
            throwable = null,
            marker = marker,
            format = format,
            arg1,
            arg2
        )
    }

    override fun error(marker: Marker?, format: String?, vararg arguments: Any?) {
        log(
            level = LogLevel.ERROR,
            throwable = null,
            marker = marker,
            format = format,
            *arguments
        )
    }

    override fun error(marker: Marker?, msg: String?, t: Throwable?) {
        log(
            level = LogLevel.ERROR,
            throwable = t,
            marker = marker,
            message = msg,
        )
    }

    private fun log(
        level: LogLevel,
        throwable: Throwable?,
        marker: Marker?,
        message: String?,
    ) {
        log.log(
            level = level,
            throwable = throwable,
            time = Instant.now(),
            messageContext = createContextMap(marker),
        ) {
            message ?: ""
        }
    }

    private fun isLogEnabled(level: LogLevel, marker: Marker?): Boolean {
        return mainLogger.appenders.any { (_, appender) ->
            appender.filter.isLogEnabled(
                LogMessage(
                    contentSupplier = { "" },
                    time = Instant.now(),
                    throwable = null,
                    level = level,
                    loggerName = log.name,
                    messageContext = createContextMap(marker)
                )
            ) == LogFilter.Result.ALLOW
        }
    }

    private fun log(
        level: LogLevel,
        throwable: Throwable?,
        marker: Marker?,
        format: String?,
        vararg args: Any?
    ) {
        log.log(
            level = level,
            throwable = throwable,
            time = Instant.now(),
            messageContext = createContextMap(marker),
        ) {
            format?.format(*args) ?: ""
        }
    }

    private fun createContextMap(marker: Marker?): Map<String, out Any> {
        return if (marker == null) {
            MDC.getCopyOfContextMap() ?: emptyMap()
        } else {
            val map = HashMap<String, Any>()
            map.putAll(MDC.getCopyOfContextMap())
            val markerList = mutableListOf<String>()
            markerList.putMarkerRecursive(marker)
            map["sl4jMarkers"] = markerList
            map
        }
    }

    private fun MutableList<String>.putMarkerRecursive(marker: Marker) {
        add(marker.name)
        marker.iterator().forEach {
            putMarkerRecursive(it)
        }
    }
}
