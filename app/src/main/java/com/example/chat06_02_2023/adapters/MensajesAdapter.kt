package com.example.chat06_02_2023.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat06_02_2023.R
import com.example.chat06_02_2023.prefs.Mensajes

class MensajesAdapter(var lista: ArrayList<Mensajes>): RecyclerView.Adapter<MensajesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.mensaje_layout, parent, false)
        return MensajesViewHolder(v)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MensajesViewHolder, position: Int) {
        holder.render(lista[position])
    }
}