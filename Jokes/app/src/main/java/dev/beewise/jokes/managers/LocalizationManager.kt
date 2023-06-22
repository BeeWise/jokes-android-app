package dev.beewise.jokes.managers

import androidx.core.os.ConfigurationCompat

class LocalizationManager {
    companion object {
        val instance = LocalizationManager()
    }

    fun preferredLocalization(): String? {
        val context = ContextManager.instance.context?.get() ?: return null
        return ConfigurationCompat.getLocales(context.resources.configuration).get(0)?.language
    }
}