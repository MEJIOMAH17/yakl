package com.github.mejiomah17.yakl.html

import com.github.mejiomah17.yakl.core.LogAppender
import com.github.mejiomah17.yakl.core.LogFilter
import com.github.mejiomah17.yakl.core.LogMessage
import java.io.Closeable
import java.io.File
import java.time.format.DateTimeFormatter

public class HtmlRollingFileLogAppender(
    override val name: String,
    override val filter: LogFilter,
    public val rollFileWhen: (File) -> Boolean,
    /**
     * function which created new existed file for append
     */
    public val fileCreator: () -> File,
    private val dateFormatter: DateTimeFormatter,
) : LogAppender, Closeable {
    @Volatile
    private var file: File = fileCreator()

    @Volatile
    private var writer = file.outputStream().writer()
    private val lock = Any()

    init {
        writeFileStart()
    }

    override fun append(logMessage: LogMessage) {
        val row = buildString {
            append("    <tr>")
            fun td(clazz: String? = null, content: Any) {
                append("<td")
                if (clazz != null) {
                    append(" class=\"$clazz\"")
                }
                append(">")
                append(content)
                append("</td>")
            }
            td("Time", dateFormatter.format(logMessage.time))
            td("Level", logMessage.level)
            td("Logger", logMessage.loggerName)
            td(content = logMessage.messageContext)
            td(content = logMessage.content)
            appendLine("</tr>")
        }
        synchronized(lock) {
            writer.write(row)
            writer.flush()
            if (rollFileWhen(file)) {
                writeFileEnd()
                writer.close()
                file = fileCreator()
                writer = file.outputStream().writer()
            }
        }
    }

    private fun writeFileStart() {
        synchronized(lock) {
            writer.append(
                """
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <style type="text/css">
        table {
            width: 100%;

        }

        TR.even {
            background: #FFFFFF;
        }

        TR.odd {
            background: #EAEAEA;
        }

        TR.WARN {
            background: orange;
        }

        TR.ERROR, TR.FATAL {
            background: red;
            color: white;
        }

        TD {
            padding-right: 1ex;
            padding-left: 1ex;
            border-right: 2px solid #AAA;
        }

        TD.Time {
            text-align: left;
            font-family: courier, monospace;
            width: 20ch;
            font-size: smaller;
        }

        TD.Level {
            text-align: right;
            width: 5ch;
        }

        TD.Logger {
            text-align: left;
        }

        TR.header {
            background: #111111;
            color: #FFF;
            font-weight: bold;
            font-size: larger;
        }

    </style>
    <script type="application/javascript" defer>
        document.addEventListener("DOMContentLoaded", function (event) {
            var table = document.getElementById("log_table")
            for (var i = 0, row; row = table.rows[i]; i++) {
                var clazz
                if (i % 2 === 0) {
                    clazz = "even"
                } else {
                    clazz = "odd"
                }
                row.classList.add(clazz)
                var level = row.cells[1].textContent
                row.classList.add(level)
            }
        })

    </script>
    <title>YAKL LOG</title>
</head>
<body>

<table id="log_table">
    <tr class="header">
        <td class="Time">Time</td>
        <td class="Level">Level</td>
        <td class="Logger">Logger</td>
        <td>Context</td>
        <td>Message</td>
    </tr>
"""
            )
        }
    }

    private fun writeFileEnd() {
        synchronized(lock) {
            writer.write(
                """
                </table>
                </body>
            """.trimIndent()
            )
        }
    }

    override fun close() {
        synchronized(lock) {
            writeFileEnd()
            writer.close()
        }
    }
}