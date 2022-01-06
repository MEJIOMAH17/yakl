package com.github.mejiomah17.yakl.api

import java.time.Instant

public interface Logger {
    public val name: String
    public fun log(
        level: LogLevel,
        throwable: Throwable?,
        time: Instant,
        messageContext: Map<String, Any>,
        contentSupplier: () -> Any
    )

    public fun trace(
        throwable: Throwable? = null,
        time: Instant = Instant.now(),
        messageContext: Map<String, Any> = emptyMap(),
        contentSupplier: () -> Any
    ) {
        log(
            level = LogLevel.TRACE,
            time = time,
            throwable = throwable,
            messageContext = messageContext,
            contentSupplier = contentSupplier
        )
    }

    public fun debug(
        throwable: Throwable? = null,
        time: Instant = Instant.now(),
        messageContext: Map<String, Any> = emptyMap(),
        contentSupplier: () -> Any
    ) {
        log(
            level = LogLevel.DEBUG,
            time = time,
            throwable = throwable,
            messageContext = messageContext,
            contentSupplier = contentSupplier
        )
    }

    public fun info(
        throwable: Throwable? = null,
        time: Instant = Instant.now(),
        messageContext: Map<String, Any> = emptyMap(),
        contentSupplier: () -> Any
    ) {
        log(
            level = LogLevel.INFO,
            time = time,
            throwable = throwable,
            messageContext = messageContext,
            contentSupplier = contentSupplier
        )
    }

    public fun warn(
        throwable: Throwable? = null,
        time: Instant = Instant.now(),
        messageContext: Map<String, Any> = emptyMap(),
        contentSupplier: () -> Any
    ) {
        log(
            level = LogLevel.WARN,
            time = time,
            throwable = throwable,
            messageContext = messageContext,
            contentSupplier = contentSupplier
        )
    }

    public fun error(
        throwable: Throwable? = null,
        time: Instant = Instant.now(),
        messageContext: Map<String, Any> = emptyMap(),
        contentSupplier: () -> Any
    ) {
        log(
            level = LogLevel.ERROR,
            time = time,
            throwable = throwable,
            messageContext = messageContext,
            contentSupplier = contentSupplier
        )
    }

    public fun fatal(
        throwable: Throwable? = null,
        time: Instant = Instant.now(),
        messageContext: Map<String, Any> = emptyMap(),
        contentSupplier: () -> Any
    ) {
        log(
            level = LogLevel.FATAL,
            time = time,
            throwable = throwable,
            messageContext = messageContext,
            contentSupplier = contentSupplier
        )
    }
}
