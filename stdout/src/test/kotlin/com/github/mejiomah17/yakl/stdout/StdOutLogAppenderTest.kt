// package com.github.mejiomah17.yakl.stdout
//
// import com.github.mejiomah17.yakl.api.LogLevel
// import com.github.mejiomah17.yakl.core.LogFilter
// import com.github.mejiomah17.yakl.core.LogFormatter
// import com.github.mejiomah17.yakl.core.LogMessage
// import io.kotest.matchers.shouldBe
// import io.kotest.matchers.types.shouldBeSameInstanceAs
// import org.junit.jupiter.api.Test
// import java.io.PrintStream
// import java.time.Instant
//
// class StdOutLogAppenderTest {
//     @Test
//     fun shouldAppendToStdOut() {
//         lateinit var messageSlot: LogMessage
//         val message = LogMessage(
//             contentSupplier = {
//             },
//             time = Instant.now(),
//             throwable = null,
//             level = LogLevel.INFO,
//             loggerName = "123",
//             messageContext = emptyMap()
//         )
//         val appender = StdOutLogAppender(
//             "test", filter = LogFilter.allowAll,
//             formatter = LogFormatter {
//                 messageSlot = it
//                 "bla bla bla"
//             }
//         )
//         appender.filter shouldBe LogFilter.allowAll
//         val out: PrintStream = System.out
//         val messages = arrayListOf<String>()
//         try {
//             val newStream = object : PrintStream(out) {
//                 override fun println(x: Any) {
//                     messages.add(x as String)
//                 }
//             }
//             System.setOut(newStream)
//             appender.append(message)
//             messageSlot shouldBeSameInstanceAs message
//             messages.first() shouldBe "bla bla bla"
//         } finally {
//             System.setOut(out)
//         }
//     }
// }
