package com.example.chat06_02_2023.models

data class Mensajes (
    val texto: String?=null,
    val email: String?=null,
    val fecha: Long=System.currentTimeMillis(),
)