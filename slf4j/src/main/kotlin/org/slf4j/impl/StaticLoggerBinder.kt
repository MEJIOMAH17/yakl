package org.slf4j.impl

import com.github.mejiomah17.yakl.api.LoggerFather
import com.github.mejiomah17.yakl.core.MainLoggerHolder
import com.github.mejiomah17.yakl.slf4j.Slf4jLoggerFactory
import org.slf4j.ILoggerFactory
import org.slf4j.spi.LoggerFactoryBinder

public class StaticLoggerBinder<T>(
    loggerFather: T,
) : LoggerFactoryBinder where T : LoggerFather, T : MainLoggerHolder {
    private val slf4jLoggerFactory = Slf4jLoggerFactory(loggerFather)

    public companion object {
        public lateinit var instance: StaticLoggerBinder<*>

        /**
         * Declare the version of the SLF4J API this implementation is compiled
         * against. The value of this field is usually modified with each release.
         */
        // to avoid constant folding by the compiler, this field must *not* be final
        @JvmField
        public var REQUESTED_API_VERSION: String? = "1.7.32" // !final

        @JvmStatic
        public fun getSingleton(): StaticLoggerBinder<*> {
            if (!this::instance.isInitialized) {
                System.err.println(
                    "StaticLoggerBinder instance is not initialized." +
                        " You could use registerSlf4jAdapter() func in logging{} function" +
                        " or init it by yourself"
                )
            }
            return instance
        }
    }

    override fun getLoggerFactory(): ILoggerFactory {
        return slf4jLoggerFactory
    }

    override fun getLoggerFactoryClassStr(): String? {
        return slf4jLoggerFactory::class.qualifiedName
    }
}
