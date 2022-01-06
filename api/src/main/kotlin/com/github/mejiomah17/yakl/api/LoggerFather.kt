package com.github.mejiomah17.yakl.api

public interface LoggerFather {
    public fun createLogger(name: String): Logger
//    TODO steal from mu logging
//    public fun createLogger(block:()->Unit)
}
