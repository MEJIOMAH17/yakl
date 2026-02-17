package com.github.mejiomah17.yakl.slf4j

import com.github.mejiomah17.yakl.api.LoggerFather
import com.github.mejiomah17.yakl.core.MainLoggerHolder
import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider

public class YaklSLF4JServiceProvider : SLF4JServiceProvider {
    public companion object {
        private lateinit var father: LoggerFather
        private lateinit var mainLoggerHolder: MainLoggerHolder

        public fun setup(
            father: LoggerFather,
            mainLoggerHolder: MainLoggerHolder,
        ) {
            this.father = father
            this.mainLoggerHolder = mainLoggerHolder
        }

        private fun getLoggerFactory(): ILoggerFactory {
            if (!YaklSLF4JServiceProvider::father.isInitialized) {
                System.err.println(
                    "YaklSLF4JServiceProvider instance is not initialized." +
                        " You could use registerSlf4jAdapter() func in logging{} function" +
                        " or init it by yourself",
                )
            }
            return Slf4jLoggerFactory(father, mainLoggerHolder)
        }
    }

    override fun getLoggerFactory(): ILoggerFactory = YaklSLF4JServiceProvider.getLoggerFactory()

    override fun getMarkerFactory(): IMarkerFactory = BasicMarkerFactory()

    override fun getMDCAdapter(): MDCAdapter = BasicMDCAdapter()

    override fun getRequestedApiVersion(): String = "2.0.7"

    override fun initialize() {
    }
}
