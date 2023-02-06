package com.example.chat06_02_2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import com.example.chat06_02_2023.adapters.MensajesAdapter
import com.example.chat06_02_2023.databinding.ActivityChatBinding
import com.example.chat06_02_2023.prefs.Mensajes
import com.example.chat06_02_2023.prefs.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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
        setRecycler()
        setListeners()
    }

    private fun setListeners() {

    }

    private fun setRecycler() {

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
        }
        return super.onOptionsItemSelected(item)
    }
}