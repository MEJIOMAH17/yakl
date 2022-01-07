package com.github.mejiomah17.yakl.dsl

import org.slf4j.impl.StaticLoggerBinder

public fun LogDslScope.registerSlf4jAdapter() {
    afterCreationHooks.add {
        StaticLoggerBinder.instance = StaticLoggerBinder(it)
    }
}
