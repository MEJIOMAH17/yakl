package com.github.mejiomah17.yakl.dsl

import com.github.mejiomah17.yakl.core.LogAppender
import com.github.mejiomah17.yakl.core.MainLogger
import com.github.mejiomah17.yakl.core.SimpleLoggerFather
import com.github.mejiomah17.yakl.core.sync.SyncMainLogger

public class LogDslScope {
    public var mainLoggerSupplier: (Collection<LogAppender>) -> MainLogger = SyncMainLogger
    public val appenders: MutableCollection<LogAppender> = arrayListOf()
    public val afterCreationHooks: MutableCollection<(SimpleLoggerFather)->Unit> = arrayListOf()
}
