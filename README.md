# YAKL

Yet Another Kotlin Logger.

### Motivation

Current jvm loggers have some disadvantages. They have

1) Magic configuration.  
   a) Implicit implementation selector via classpath scan. If you have multiple implementations, they are blow up. It's
   annoying with transitive dependencies.  
   b) Configuration via hierarchical scan of XMLs in classpath.  
   c) Developer should read documentation which dependency he has to add to classpath for usage new XML tags  
   d) Developer could write wrong configuration, because it's XML without compiler checks
2) Does not support default args and lambdas. In order, API, which should be simple, has to many methods, and a little
   ugly. For convenient API we use wrappers like [kotlin-logging](https://github.com/MicroUtils/kotlin-logging)
3) Implicit usage  
   a) Passing log context via ThreadLocals is implicit. It creates errors when you use concurrency. I know about
   solutions (CustomThreadPools, or CoroutineDispatcher), but it's ugly.  
   b) Implicit logger creation via static methods.
4) Overcomplicated  
   a) Logger inheritance, JNDI calls, and many others "features". Most server side usages, just would like write Strings
   to stdout, and store them to ELK, Splunk, etc.

This project is attempt to create new logger for kotlin-jvm without these "mistakes"

### Features

1) Code configuration. Use kotlin-dsl for logger configuration. You could override almost anything. Yep, you could do it
   in runtime (if you need it).
2) Simple api. Look into ```api``` module and read three simple classes.
3) Explicit usage. You pass everything explicitly. If you need implicit usage, you could easy wrap Logger and store
   context in ThreadLocals, CoroutineContexts or your custom storage.

### Modules

1) api - use this module if you write a library which should log something.
2) stdout - use this module in your application for log something to stdout.
3) html - use this module in your application for log something to html files.
4) core - use this module if you would like extend standard realization of YAKL
5) slf4j - adapter for slf4j.

### Current Status

YAKL in alpha testing. It does not have guarantees for API and support.

Project will change status to beta testing when it receives

1) Quality Gates
2) Deployment
3) 50 stars on github. (Community like it, and this is not waste of time)

### Example

```kotlin
val loggerFather = logging {
    stdout("stdout") {
        filter = LoggerNameFilter(
            Regex(".*") to LogLevel.INFO,
            Regex("example.*") to LogLevel.DEBUG
        )
        formatter = LogFormatter { builder, msg ->
            val time = dateFormatter.format(msg.time)
            builder.append("$time [${msg.level}] ${msg.loggerName} : ${msg.content}")
        }
    }
    registerSlf4jAdapter()
}
val logger = loggerFather.createLogger(name = "my-new-logger")

```