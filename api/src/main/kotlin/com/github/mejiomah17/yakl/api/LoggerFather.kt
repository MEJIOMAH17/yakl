package com.github.mejiomah17.yakl.api

public interface LoggerFather {
    public fun createLogger(name: String): Logger
}

public fun LoggerFather.createLogger(block: () -> Unit): Logger {
    return createLogger(name(block))
}

private fun name(func: () -> Unit): String {
    val name = func.javaClass.name
    val slicedName = when {
        name.contains("Kt$") -> name.substringBefore("Kt$")
        name.contains("$") -> name.substringBefore("$")
        else -> name
    }
    return slicedName
}
