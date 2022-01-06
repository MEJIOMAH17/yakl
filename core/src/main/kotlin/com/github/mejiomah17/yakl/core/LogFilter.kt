package com.github.mejiomah17.yakl.core

public fun interface LogFilter {
    public fun isLogEnabled(logMessage: LogMessage): Result
    public enum class Result {
        /**
         * Allow logging
         */
        ALLOW,

        /**
         * Deny logging
         */
        DENY,

        /**
         * Delegate to next filter
         */
        NEXT,
    }

    public companion object {
        public val allowAll: LogFilter = LogFilter {
            Result.ALLOW
        }
    }
}
