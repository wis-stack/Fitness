package com.example.fitnessforfutureassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!(auth.currentUser?.uid.isNullOrBlank())) {
            auth.signOut()
        }

        txtMainSignup.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        btnMainLogIn.setOnClickListener {
            if (edtMainEmail.text.toString().isNullOrBlank()) {
                edtMainTLEmail.error = "Cannot be Blank"
            } else {
                edtMainTLEmail.error = null
            }
            if (edtMainPassword.text.toString().isNullOrBlank()) {
                edtMainTLPassword.error = "Cannot be Blank"
            } else {
                edtMainTLPassword.error = null
            }
            if (edtMainTLEmail.error == null && edtMainPassword.error == null){
                auth.signInWithEmailAndPassword(edtMainEmail.text.toString().trim(), edtMainPassword.text.toString().trim()).addOnCompleteListener { status ->
                    if (status.isSuccessful) {
                        val intent = Intent(this, HomePage::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Invalid User", Toast.LENGTH_LONG).show()
                        edtMainTLEmail.error = "try again"
                        edtMainTLPassword.error = "try again"
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        edtMainEmail.setText("")
        edtMainPassword.setText("")
        edtMainTLPassword.error = null
        edtMainTLEmail.error = null
        auth.signOut()
    }

}