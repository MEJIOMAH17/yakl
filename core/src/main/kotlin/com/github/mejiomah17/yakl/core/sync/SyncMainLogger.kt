package com.github.mejiomah17.yakl.core.sync

import com.github.mejiomah17.yakl.core.LogAppender
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import com.github.mejiomah17.yakl.core.MainLogger
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

public class SyncMainLogger(
    appenders: Collection<LogAppender>
) : MainLogger {
    override val appenders: ConcurrentMap<String, LogAppender> =
        appenders.associateBy { it.name }.toMap(ConcurrentHashMap())

    public override fun log(logMessage: LogMessage) {
        appenders.forEach { (_, appender) ->
            if (appender.filter.isLogEnabled(logMessage) == LogFilter.Result.ALLOW) {
                appender.append(logMessage)
            }
        }
    }

    public companion object : (Collection<LogAppender>) -> SyncMainLogger {
        override fun invoke(appenders: Collection<LogAppender>): SyncMainLogger {
            return SyncMainLogger(appenders)
        }
    }
}
