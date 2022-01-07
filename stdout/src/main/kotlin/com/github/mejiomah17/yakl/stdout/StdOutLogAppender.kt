package com.github.mejiomah17.yakl.stdout

import com.github.mejiomah17.yakl.core.LogAppender
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogFormatter
import com.github.mejiomah17.yakl.core.LogMessage

public class StdOutLogAppender(
    override val name: String,
    override val filter: LogFilter,
    public val formatter: LogFormatter,
) : LogAppender {
    private val out = System.out
    override fun append(logMessage: LogMessage) {
        val x = buildString {
            formatter.format(this, logMessage)
            appendLine()
        }
        out.writeBytes(x.toByteArray())
        out.flush()
    }
}
