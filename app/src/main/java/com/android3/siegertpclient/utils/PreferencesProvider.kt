package com.android3.siegertpclient.utils

import android.content.Context
import com.android3.siegertpclient.data.tournament.Tournament
import com.android3.siegertpclient.data.user.User
import com.android3.siegertpclient.ui.dummyretrofit.util.Constants.Companion.KEY_CURRENT_TOURNAMENT
import com.android3.siegertpclient.ui.dummyretrofit.util.Constants.Companion.KEY_USER
import com.google.gson.Gson

class PreferencesProvider(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE)

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String) : Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key : String) : String? {
        return sharedPreferences.getString(key, "")
    }

    fun putUser(user: User) {
        val json = Gson().toJson(user)
        putString(KEY_USER, json)
    }

    fun getUser(key: String) : User? {
        val json = getString(KEY_USER)
        return if (json != null) Gson().fromJson(json, User::class.java) else null
    }

    fun putCurrentTournament(tournament : Tournament) {
        val json = Gson().toJson(tournament)
        putString(KEY_CURRENT_TOURNAMENT, json)
    }

    
    fun getCurrentTournament(tournamentKey : String) : Tournament? {
        val json = getString(KEY_CURRENT_TOURNAMENT)
        return if (json != null) Gson().fromJson(json, Tournament::class.java) else null
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}