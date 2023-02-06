package com.example.chat06_02_2023.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chat06_02_2023.databinding.MensajeLayoutBinding
import com.example.chat06_02_2023.prefs.Mensajes
import java.text.SimpleDateFormat
import java.util.*

class MensajesViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val binding = MensajeLayoutBinding.bind(v)

    fun render(mensaje: Mensajes) {
        binding.tvMensaje.text = mensaje.texto
        binding.tvEmail.text = mensaje.email
        binding.tvHora.text = convertLongToDate(mensaje.fecha)
    }

    private fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("h:m:ss a dd/MM/yyyy")
        return format.format(date)
    }
}
