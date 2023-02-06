package com.example.chat06_02_2023.prefs

import android.content.Context

class Prefs(c: Context) {
    val storage = c.getSharedPreferences("chat", 0)

    fun getEmail(): String? {
        return storage.getString("email", null)
    }

    fun setEmail(email: String) {
        storage.edit().putString("email", email).apply()
    }

    fun borrarTodo() {
        storage.edit().clear().apply()
    }
}