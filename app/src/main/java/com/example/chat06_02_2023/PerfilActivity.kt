package com.example.chat06_02_2023

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.chat06_02_2023.databinding.ActivityPerfilBinding
import com.example.chat06_02_2023.prefs.Prefs
import com.google.firebase.storage.FirebaseStorage

class PerfilActivity : AppCompatActivity() {
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
        if (uri != null) {
            binding.ivPerfil.setImageURI(uri)
            guardarImagen(uri)
        } else {

        }
    }

    private fun guardarImagen(uri: Uri) {
        val ref = storage.reference
        val imagen = ref.child("$email/perfil.jpg")
        val upload = imagen.putFile(uri).addOnSuccessListener {
            //Se ha subido la imagen
            finish()
        } .addOnFailureListener() {
            //No se ha subido la imagen
            println(it.message.toString())
        }

    }


    lateinit var storage: FirebaseStorage

    lateinit var binding: ActivityPerfilBinding
    lateinit var prefs: Prefs
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = Prefs(this)
        email = prefs.getEmail().toString()
        storage = FirebaseStorage.getInstance("gs://proyecto-chat060223.appspot.com/")
        setListeners()
    }

    private fun setListeners() {
        binding.btnPerfil.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
}