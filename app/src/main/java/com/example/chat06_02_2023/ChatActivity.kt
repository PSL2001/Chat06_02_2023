package com.example.chat06_02_2023

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat06_02_2023.adapters.MensajesAdapter
import com.example.chat06_02_2023.databinding.ActivityChatBinding
import com.example.chat06_02_2023.models.Mensajes
import com.example.chat06_02_2023.prefs.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var prefs: Prefs
    lateinit var adapter: MensajesAdapter
    lateinit var db : FirebaseDatabase

    var email = ""
    private var lista = ArrayList<Mensajes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        prefs = Prefs(this)
        setContentView(binding.root)
        email = prefs.getEmail().toString()
        db = FirebaseDatabase.getInstance("https://proyecto-chat060223-default-rtdb.europe-west1.firebasedatabase.app/")
        setRecycler()
        traerMensajes()
        setListeners()
    }

    private fun traerMensajes() {
        db.getReference("chat").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()
                if (snapshot.exists()) {
                    for (item in snapshot.children) {
                        var mensaje = item.getValue(Mensajes::class.java)
                        if (mensaje != null) {
                            lista.add(mensaje)
                        }
                    }
                    lista.sortBy { mensaje -> mensaje.fecha }
                    adapter.lista = lista
                    adapter.notifyDataSetChanged()
                    binding.recChat.scrollToPosition(lista.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun setListeners() {
        binding.ivSend.setOnClickListener {
            enviarMensaje()
            it.ocultarTeclado()
        }

    }

    private fun enviarMensaje() {
        val texto = binding.teiChat.text.toString().trim()
        if (texto.isEmpty()) return
        val mensaje = Mensajes(texto = texto, email = email)
        db.getReference("chat").child(mensaje.fecha.toString()).setValue(mensaje).addOnSuccessListener {
            traerMensajes()
            binding.teiChat.setText("")
        }
            .addOnFailureListener {

            }

    }

    private fun setRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.recChat.layoutManager = layoutManager
        adapter = MensajesAdapter(lista, email)
        binding.recChat.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_opciones, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                FirebaseAuth.getInstance().signOut()
                prefs.borrarTodo()
                finish()
            }
            R.id.item_salir -> {
                finishAffinity()
            }
            R.id.item_perfil -> {
                startActivity(Intent(this, PerfilActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Oculta el teclado, se activa cuando se hace click en el botón de enviar
     */
    private fun View.ocultarTeclado() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onRestart() {
        traerMensajes()
        super.onRestart()
    }
}