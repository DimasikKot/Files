package org.koolda.files.expect

import android.content.Context

class Database(context: Context) {
    private val prefs = context.getSharedPreferences("databaseInfo1", Context.MODE_PRIVATE)

    fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue)!!
    }

    fun setString(key: String, value: String) {
        return prefs.edit().putString(key, value).apply()
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return prefs.getFloat(key, defaultValue)
    }

    fun setFloat(key: String, value: Float) {
        return prefs.edit().putFloat(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        return prefs.edit().putBoolean(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        return prefs.edit().putInt(key, value).apply()
    }
}