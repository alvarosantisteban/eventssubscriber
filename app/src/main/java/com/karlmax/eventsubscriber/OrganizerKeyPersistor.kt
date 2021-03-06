package com.karlmax.eventsubscriber

import android.content.Context

/**
 * Manages the organizers' name-IDs in the SharedPreferences.
 */
class OrganizerKeyPersistor {

    companion object {

        private val PERSISTOR_PREF_NAME = "PERSISTOR_PREF_NAME"
        private val PERSISTOR_ORGANIZER_KEY_KEY = "PERSISTOR_ORGANIZER_KEY_KEY"
        private val ORGANIZER_KEY_SEPARATOR = ";"
    }

    fun getPersistedKeys(context: Context): ArrayList<String> {
        val prefs = context.getSharedPreferences(PERSISTOR_PREF_NAME, Context.MODE_PRIVATE)
        val organizerRawString = prefs.getString(PERSISTOR_ORGANIZER_KEY_KEY, "")
        return if (organizerRawString.isNotBlank())
            ArrayList(organizerRawString.split(";").map { it })
        else
            ArrayList()
    }

    fun persistKey(context: Context, key: String) {
        val keys = getPersistedKeys(context)
        if(!keys.contains(key)) {
            keys.add(key)
            persistKeyList(context, keys)
        }
    }

    fun removeKey(context: Context, id: String) {
        val keys = getPersistedKeys(context)
        keys.remove(id)
        persistKeyList(context, keys)
    }

    fun isEmpty(context: Context) : Boolean {
        return getPersistedKeys(context).size < 1
    }

    private fun persistKeyList(context: Context, keys: ArrayList<String>) {
        context.getSharedPreferences(PERSISTOR_PREF_NAME, Context.MODE_PRIVATE).edit()
                .putString(PERSISTOR_ORGANIZER_KEY_KEY, keys.joinToString(ORGANIZER_KEY_SEPARATOR)).apply()
    }
}