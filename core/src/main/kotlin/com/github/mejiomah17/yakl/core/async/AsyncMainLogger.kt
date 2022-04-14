package com.github.mejiomah17.yakl.core.async

import com.github.mejiomah17.yakl.core.LogAppender
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import com.github.mejiomah17.yakl.core.MainLogger
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Naive realization of AsyncLogging
 */
public class AsyncMainLogger(
    public val executorService: ExecutorService,
    appenders: Collection<LogAppender>
) : MainLogger {
    override val appenders: ConcurrentMap<String, LogAppender> =
        appenders.associateBy { it.name }.toMap(ConcurrentHashMap())

    @Volatile
    private var closing = false

    /**
     * submit log message to different threads for [appenders]
     */
    public override fun log(logMessage: LogMessage) {
        if (closing) {
            throw java.lang.IllegalStateException("can't log message. Logger is closed")
        }
        appenders.forEach { _, appender ->
            executorService.submit {
                if (appender.filter.isLogEnabled(logMessage) == LogFilter.Result.ALLOW) {
                    appender.append(logMessage)
                }
            }
        }
    }

    override fun close() {
        closing = true
        executorService.shutdown()
        while (!executorService.isShutdown) {
            Thread.sleep(100)
        }
        super.close()
    }

    public companion object : (Collection<LogAppender>) -> AsyncMainLogger {
        override fun invoke(appenders: Collection<LogAppender>): AsyncMainLogger {
            return AsyncMainLogger(Executors.newFixedThreadPool(appenders.size), appenders)
        }
    }
}
