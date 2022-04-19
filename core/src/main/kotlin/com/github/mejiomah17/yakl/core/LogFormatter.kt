package com.github.mejiomah17.yakl.core

import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

public fun interface LogFormatter {
    public fun format(builder: StringBuilder, message: LogMessage)

    public companion object {
        private val defaultDateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.ms").withZone(ZoneId.from(ZoneOffset.UTC))
        public val defaultFormatter: LogFormatter = LogFormatter { builder, message ->
            val time = defaultDateFormatter.format(message.time)
            val context = if (message.messageContext.isNotEmpty()) {
                "${message.messageContext} "
            } else {
                ""
            }
            builder.append("$time [${message.level}] ")
            builder.append("${context}${message.loggerName}:${message.content}")
            if (message.throwable != null) {
                builder.append("${message.throwable} ${message.throwable.stackTraceToString()}")
            }
        }
    }
}
