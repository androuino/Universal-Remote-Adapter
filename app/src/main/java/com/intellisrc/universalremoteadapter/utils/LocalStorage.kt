package com.intellisrc.universalremoteadapter.utils

import android.content.SharedPreferences
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by joshua on 17/09/19.
 * A wrapper/abstraction layer over SharedPreferences which features storage/retrieving data from/to SharedPreferences
 */

@Singleton
class LocalStorage @Inject constructor(private val sharedPrefs: SharedPreferences) {

    // Get instance of Editor
    private val editor: SharedPreferences.Editor
        get() = sharedPrefs.edit()

    // Get locally stored value for specific key
    fun getValue(key: String, defaultValue: String): String? {
        var value: String? = ""
        val sharedPrefs = sharedPrefs
        try {
            value = sharedPrefs.getString(key, defaultValue)
        } catch (e: Exception) {
            Timber.e("Error getting value for key: $key\nError: ${e.message}")
        }

        return value
    }

    fun getValue(key: String, defaultValue: Int): Int {
        var value = 0
        val sharedPrefs = sharedPrefs
        try {
            value = sharedPrefs.getInt(key, defaultValue)
        } catch (e: Exception) {
            Timber.e("Error getting value for key: $key\nError: ${e.message}")
        }

        return value
    }

    fun getValue(key: String, defaultValue: Boolean): Boolean? {
        var value: Boolean = defaultValue
        val sharedPrefs = sharedPrefs
        try {
            value = sharedPrefs.getBoolean(key, defaultValue)
        } catch (e: Exception) {
            Timber.e("Error getting value for key: $key\nError: ${e.message}")
        }

        return value
    }

    fun getValue(key: String, defaultValue: Float?): Float? {
        var value: Float? = 0f
        val sharedPrefs = sharedPrefs
        try {
            value = sharedPrefs.getFloat(key, defaultValue!!)
        } catch (e: Exception) {
            Timber.e("Error getting value for key: $key\nError: ${e.message}")
        }

        return value
    }

    /**
     * Getting the label data for localization using the data saved from the server
     *
     * If the key does not exist, create the key and insert an empty string, this will be used for debugging
     * @param key - the string key
     *
     */
    fun getLabel(key: String): String {
        var value = ""
        try {
            value = sharedPrefs.getString(key, "").toString()
        } catch (e: Exception) {
            Timber.e("Error getting value for key: $key\nError: ${e.message}")
        }

        if (value == "") putValue(key, "")
        return value
    }

    // Store data locally for specific key
    fun putValue(key: String, value: Any) {
        val newVal: Any
        val editor = editor
        when (value) {
            is String -> {
                newVal = value
                editor.putString(key, newVal)
            }
            is Int -> {
                newVal = value
                editor.putInt(key, newVal)
            }
            is Float -> {
                newVal = value
                editor.putFloat(key, newVal)
            }
            is Boolean -> {
                newVal = value
                editor.putBoolean(key, newVal)
            }
            is Long -> {
                newVal = value
                editor.putLong(key, newVal)
            }
        }
        editor.commit()
    }

    fun saveUserName(name: String?) {
        name?.let { putValue("USERNAME", name) }
    }

    fun getUserName(): String? {
        return getValue("USERNAME", "NAME")
    }

    fun saveWorkerLabel(name: String?) {
        name?.let { putValue("WORKER_LABEL", name) }
    }

    fun getWorkerLabel(): String? {
        return getValue("WORKER_LABEL", "WORKER")
    }

    fun saveLeaderLabel(name: String?) {
        name?.let { putValue("LEADER_LABEL", name) }
    }

    fun getLeaderLabel(): String? {
        return getValue("LEADER_LABEL", "LEADER")
    }

    fun saveUserType(name: String?) {
        name?.let { putValue("USERTYPE", name) }
    }

    fun getUserType(): String? {
        return getValue("USERTYPE", "UNSET")
    }

    fun getAlertLimit(): Int? {
        return getValue("ALERT_LIMIT", 20)
    }

    fun saveAlertLimit(limit: Int?) {
        limit?.let { putValue("ALERT_LIMIT", limit) }
    }
}
