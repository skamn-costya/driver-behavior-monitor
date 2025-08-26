package com.example.driverbehaviormonitor

import android.content.Context

object SessionManager {
    private lateinit var appContext: Context
    var accessToken: String? = null
    var refreshToken: String? = null
    var isSet: Boolean = false

    fun init(context: Context) {
        appContext = context.applicationContext
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        accessToken = prefs.getString("access_token", null)
        refreshToken = prefs.getString("refresh_token", null)
        if (!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty())
            isSet = true
        else
            isSet = false
    }

    fun saveTokens(access: String, refresh: String) {
        accessToken = access
        refreshToken = refresh
        val prefs = appContext.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("access_token", access)
            .putString("refresh_token", refresh)
            .apply()
        isSet = true
    }

    fun deleteTokens() {
        val prefs = appContext.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("access_token", "")
            .putString("refresh_token", "")
            .apply()
        isSet = false
    }
}