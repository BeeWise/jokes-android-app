package dev.beewise.jokes.managers

import android.content.Context
import java.lang.ref.WeakReference

public class ContextManager {
    public var context: WeakReference<Context>? = null

    public companion object {
        public val instance = ContextManager()
    }
}