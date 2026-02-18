package com.github.mejiomah17.yakl.dsl

import com.github.mejiomah17.yakl.slf4j.YaklSLF4JServiceProvider

public fun LogDslScope.registerSlf4jAdapter() {
    afterCreationHooks.add {
        YaklSLF4JServiceProvider.setup(it, it)
    }
}
