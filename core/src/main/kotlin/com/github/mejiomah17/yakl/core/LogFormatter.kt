package com.github.mejiomah17.yakl.core

public fun interface LogFormatter {
    public fun format(builder: StringBuilder, message: LogMessage)

    public companion object {
        public val simpleFormatter: LogFormatter = LogFormatter { builder, message ->
            builder.append(
                "LogMessage(throwable=${message.throwable}, level=${message.level}, loggerName='${message.loggerName}', messageContext=${message.messageContext}, content=${message.content})"
            )
        }
    }
}
