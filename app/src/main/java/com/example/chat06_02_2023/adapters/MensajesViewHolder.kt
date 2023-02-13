package com.example.chat06_02_2023.adapters

import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.chat06_02_2023.R
import com.example.chat06_02_2023.databinding.MensajeLayoutBinding
import com.example.chat06_02_2023.models.Mensajes
import com.example.chat06_02_2023.prefs.Prefs
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class MensajesViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val binding = MensajeLayoutBinding.bind(v)
    var storage = FirebaseStorage.getInstance("gs://proyecto-chat060223.appspot.com/")
    //var prefs = Prefs(v.context)
    fun render(mensaje: Mensajes, email: String) {
        binding.tvMensaje.text = mensaje.texto
        binding.tvEmail.text = mensaje.email
        binding.tvHora.text = convertLongToDate(mensaje.fecha)
        /*
         //Si el mensaje es del usuario logueado, se cambia el color
        if (mensaje.email == prefs.getEmail()) {
            binding.clMensaje.setBackgroundColor(Color.parseColor("#FFCDD2"))
        } else {
            binding.clMensaje.setBackgroundColor(Color.parseColor("#E1F5FE"))
        }
         */
        if (mensaje.email == email) {
            binding.clMensaje.setBackgroundColor(ContextCompat.getColor(binding.clMensaje.context, R.color.cChat))
        } else {
            binding.clMensaje.setBackgroundColor(ContextCompat.getColor(binding.clMensaje.context, R.color.white))
        }
        ponerImagen(mensaje.email.toString())
    }

    private fun ponerImagen(email: String) {
        // Compruebo si el usuario tiene una imagen de perfil
        val storageRef = storage.reference
        val file = storageRef.child("$email/perfil.jpg")
        file.metadata.addOnSuccessListener {
            //Existe el archivo de perfil ponemos el perfil correspondiente
            //Creo la url
            file.downloadUrl.addOnSuccessListener { uri ->
                rellenarImagen(uri)
            }
        }
            .addOnFailureListener {
                //No existe el archivo de perfil, se pone la imagen por defecto
                val default = storageRef.child("default/perfil.jpg")
                default.downloadUrl.addOnSuccessListener { uri ->
                    rellenarImagen(uri)
                }
            }
    }

    private fun rellenarImagen(uri: Uri?) {
        val requestOptions = RequestOptions().transform(CircleCrop())
        Glide.with(binding.clMensaje.context)
            .load(uri)
            .centerCrop()
            .apply(requestOptions)
            .into(binding.ivPerfilChat)
    }

    private fun convertLongToDate(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("h:m:ss a dd/MM/yyyy")
        return format.format(date)
    }
}
