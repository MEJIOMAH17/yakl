package org.slf4j.impl

import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMarkerFactory

public class StaticMarkerBinder {
    public companion object {
        private val instance = StaticMarkerBinder()

        @JvmStatic
        public fun getSingleton(): StaticMarkerBinder {
            return instance
        }
    }

    public fun getMarkerFactory(): IMarkerFactory {
        return BasicMarkerFactory()
    }

    public fun getMarkerFactoryClassStr(): String? {
        return BasicMarkerFactory::class.qualifiedName
    }
}
