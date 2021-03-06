package com.github.mejiomah17.yakl.stdout

import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogFormatter
import com.github.mejiomah17.yakl.dsl.LogDslScope
import java.util.IdentityHashMap
import java.util.concurrent.atomic.AtomicInteger

private val counters = IdentityHashMap<LogDslScope, AtomicInteger>()
public fun LogDslScope.stdout(
    name: String = "sdtout-${counters.computeIfAbsent(this) { AtomicInteger() }.incrementAndGet()}",
    block: StdOutLogAppenderDslScope.() -> Unit = {}
) {
    val scope = StdOutLogAppenderDslScope(name)
    scope.block()
    appenders.add(
        StdOutLogAppender(
            name = scope.name,
            filter = scope.filter,
            formatter = scope.formatter
        )
    )
}

public class StdOutLogAppenderDslScope(public val name: String) {
    public var formatter: LogFormatter = LogFormatter.defaultFormatter
    public var filter: LogFilter = LogFilter.allowAll
}
