package com.example.chat06_02_2023.adapters

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.chat06_02_2023.databinding.MensajeLayoutBinding
import com.example.chat06_02_2023.models.Mensajes
import com.example.chat06_02_2023.prefs.Prefs
import java.text.SimpleDateFormat
import java.util.*

class MensajesViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val binding = MensajeLayoutBinding.bind(v)
    var prefs = Prefs(v.context)
    fun render(mensaje: Mensajes) {
        binding.tvMensaje.text = mensaje.texto
        binding.tvEmail.text = mensaje.email
        binding.tvHora.text = convertLongToDate(mensaje.fecha)

        //Si el mensaje es del usuario logueado, se cambia el color
        if (mensaje.email == prefs.getEmail()) {
            binding.clMensaje.setBackgroundColor(Color.parseColor("#FFCDD2"))
        } else {
            binding.clMensaje.setBackgroundColor(Color.parseColor("#E1F5FE"))
        }
    }

    private fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("h:m:ss a dd/MM/yyyy")
        return format.format(date)
    }
}
