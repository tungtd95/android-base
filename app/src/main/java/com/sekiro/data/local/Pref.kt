package com.sekiro.data.local

import android.content.Context

class Pref(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

    fun isFirstTimeStartUp() = sharedPreferences.getBoolean(FIRST_TIME_STARTUP, true)

    fun markFirstTimeStartUp() {
        sharedPreferences.edit().putBoolean(FIRST_TIME_STARTUP, false).apply()
    }

    companion object {
        private const val PREF_FILE = "PREF_FILE"

        private const val FIRST_TIME_STARTUP = "FIRST_TIME_STARTUP"
    }
}