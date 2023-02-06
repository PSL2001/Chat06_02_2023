package com.example.chat06_02_2023.prefs

data class Mensajes (
    val texto: String,
    val email: String,
    val fecha: Long=System.currentTimeMillis(),
)