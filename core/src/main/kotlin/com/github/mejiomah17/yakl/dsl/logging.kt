package com.github.mejiomah17.yakl.dsl

import com.github.mejiomah17.yakl.core.SimpleLoggerFather

public fun logging(block: LogDslScope.() -> Unit): SimpleLoggerFather {
    val scope = LogDslScope()
    scope.block()
    return SimpleLoggerFather(
        mainLogger = scope.mainLoggerSupplier(scope.appenders)
    )
}
