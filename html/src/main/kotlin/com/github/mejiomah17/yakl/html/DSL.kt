package com.github.mejiomah17.yakl.html

import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.dsl.LogDslScope
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.IdentityHashMap
import java.util.concurrent.atomic.AtomicInteger

private val counters = IdentityHashMap<LogDslScope, AtomicInteger>()

public fun LogDslScope.html(
    name: String = "sdtout-${counters.computeIfAbsent(this) { AtomicInteger() }.incrementAndGet()}",
    block: HtmlRollingFileAppenderDslScope.() -> Unit = {}
) {
    val scope = HtmlRollingFileAppenderDslScope(name)
    scope.block()
    appenders.add(
        HtmlRollingFileLogAppender(
            name = scope.name,
            filter = scope.filter,
            rollFileWhen = scope.rollFileWhen,
            fileCreator = scope.fileCreator,
            dateFormatter = scope.dateFormatter
        )
    )
}

public class HtmlRollingFileAppenderDslScope(
    public val name: String
) {
    public companion object {
        private val defaultDateFormat =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.ms").withZone(ZoneId.from(ZoneOffset.UTC))
    }

    public var filter: LogFilter = LogFilter.allowAll
    public var rollFileWhen: (File) -> Boolean = { it.length() > 10_000 }
    public var fileCreator: () -> File = {
        File("log-${defaultDateFormat.format(Instant.now())}.html")
    }
    public var dateFormatter: DateTimeFormatter = defaultDateFormat
}
