package org.slf4j.impl

import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.spi.MDCAdapter

public class StaticMDCBinder {
    public companion object {
        private val instance: StaticMDCBinder = StaticMDCBinder()

        @JvmStatic
        public fun getSingleton(): StaticMDCBinder {
            return instance
        }
    }

    public fun getMDCA(): MDCAdapter {
        return BasicMDCAdapter()
    }

    public fun getMDCAdapterClassStr(): String? {
        return BasicMDCAdapter::class.qualifiedName
    }
}
