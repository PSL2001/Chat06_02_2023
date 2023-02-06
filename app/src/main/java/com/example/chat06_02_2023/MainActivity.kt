package com.example.chat06_02_2023

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.chat06_02_2023.databinding.ActivityMainBinding
import com.example.chat06_02_2023.prefs.Prefs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val cuenta = task.getResult(ApiException::class.java)
                if (cuenta != null) {
                    val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credenciales).addOnCompleteListener {
                        if (it.isSuccessful) {
                            prefs.setEmail(cuenta.email ?: "")
                            irChat()
                        } else {
                            mostrarError("Error al iniciar sesión")
                        }
                    }
                }
            }catch (e: ApiException) {
                println(e.message.toString())
            }
        }
    }

    private fun mostrarError(s: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(s)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    lateinit var binding: ActivityMainBinding
    lateinit var prefs: Prefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        prefs = Prefs(this)
        setContentView(binding.root)
        comprobarSesion()
        setListeners()
    }

    private fun setListeners() {
        binding.googleButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("269035647166-h198g5r5oe44ro66pjvj90d23dqs92hm.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        responseLauncher.launch(googleClient.signInIntent)
    }

    private fun comprobarSesion() {
        val email = prefs.getEmail()
        if (email != null) {
            irChat()
        }
    }

    private fun irChat() {
        startActivity(Intent(this, ChatActivity::class.java))
    }
}