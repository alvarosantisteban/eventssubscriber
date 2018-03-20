package com.karlmax.eventsubscriber

import android.content.Context

class OrganizerIdPersistor {

    companion object {

        private val ID_PERSITOR_PREF_NAME = "ID_PERSITOR_PREF_NAME"
        private val ID_PERSITOR_ID_KEY = "ID_PERSITOR_ID_KEY"
        private val SEPERATOR = ";"
    }

    fun getPersistedIds(context: Context): ArrayList<Long> {
        val prefs = context.getSharedPreferences(ID_PERSITOR_PREF_NAME, Context.MODE_PRIVATE)
        return ArrayList(prefs.getString(ID_PERSITOR_ID_KEY, null).split(";").map { it.toLong() })
    }

    fun persistId(context: Context, id: Long) {
        val keys = getPersistedIds(context)
        keys.add(id)
        context.getSharedPreferences(ID_PERSITOR_PREF_NAME, Context.MODE_PRIVATE).edit()
                .putString(ID_PERSITOR_ID_KEY, keys.joinToString(SEPERATOR)).apply()
    }
}