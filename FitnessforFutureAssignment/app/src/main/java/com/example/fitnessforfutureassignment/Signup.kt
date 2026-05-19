package com.example.fitnessforfutureassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var myUser: DatabaseReference
    var email = ""
    var firstName = ""
    var lastName = ""
    var age = 0
    var phone = ""
    var height = 0
    var weight = 0
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        database = FirebaseDatabase.getInstance()
        auth = Firebase.auth
        myUser = database.getReference("Users&Info")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        btnSignUpSubmit.setOnClickListener {
            if (edtSignUpFirstName.text.toString().trim().isNullOrBlank()) {
                edtSignUpTLFirstName.error = "Cannot be blank"
                edtSignUpFirstName.setText("")
            } else {
                edtSignUpTLFirstName.error = null
                firstName = edtSignUpFirstName.text.toString().trim()
            }

            if (edtSignUpLastName.text.toString().trim().isNullOrBlank()) {
                lastName = ""
            } else {
                lastName = edtSignUpLastName.text.toString().trim()
            }

            if (edtSignUpEmail.text.toString().trim().isNullOrBlank()) {
                edtSignUpTLEmail.error = "Cannot be blank"
                edtSignUpEmail.setText("")
            } else {
                edtSignUpTLEmail.error = null
                email = edtSignUpEmail.text.toString()
            }

            if (edtSignUpPassword.text.toString().trim().isNullOrBlank()) {
                edtSignUpTLPassword.error = "Cannot be blank"
                edtSignUpPassword.setText("")
            } else if (edtSignUpPassword.text.toString().trim().length !in 8..20) {
                edtSignUpTLPassword.error = "min 8 characters, max 20 characters"
                edtSignUpPassword.setText("")
            } else {
                edtSignUpTLPassword.error = null
                password = edtSignUpPassword.text.toString().trim()
            }

            if (edtSignUpAge.text.toString().isNullOrBlank()) {
                edtSignUpTLAge.error = "Cannot be blank"
                edtSignUpAge.setText("")
            } else {
                edtSignUpTLAge.error = null
                age = edtSignUpAge.text.toString().toInt()
            }

            if (edtSignUpPhone.text.toString().isNullOrBlank()) {
                edtSignUpTLPhone.error = "Cannot be blank"
                edtSignUpPhone.setText("")
            } else {
                edtSignUpTLPhone.error = null
                phone = edtSignUpPhone.text.toString()
            }

            if (edtSignUpWeight.text.toString().isNullOrBlank()) {
                edtSignUpTLWeight.error = "Cannot be blank"
                edtSignUpWeight.setText("")
            } else {
                edtSignUpTLWeight.error = null
                weight = edtSignUpWeight.text.toString().toInt()
            }

            if (edtSignUpHeight.text.toString().isNullOrBlank()) {
                edtSignUpTLHeight.error = "Cannot be blank"
                edtSignUpHeight.setText("")
            } else {
                edtSignUpTLHeight.error = null
                height = edtSignUpHeight.text.toString().toInt()
            }

            if (edtSignUpTLFirstName.error == null && edtSignUpTLLastName.error == null && edtSignUpTLEmail.error == null &&
                edtSignUpTLPassword.error == null && edtSignUpTLPhone.error == null && edtSignUpTLAge.error == null &&
                edtSignUpTLHeight.error == null && edtSignUpTLWeight.error == null
            ) {
                auth = Firebase.auth
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { status ->
                    if(status.isSuccessful){
                        while (Firebase.auth.currentUser?.uid == null) {
                            auth = Firebase.auth
                            auth.signInWithEmailAndPassword(email,password)
                        }
                        var Uid = Firebase.auth.currentUser?.uid
                        val myUserID = myUser.child(Uid.toString())
                        myUserID.child("FirstName").setValue(firstName)
                        myUserID.child("LastName").setValue(lastName)
                        myUserID.child("Password").setValue(password)
                        myUserID.child("Phone").setValue(phone)
                        myUserID.child("Age").setValue(age)
                        myUserID.child("Weight").setValue(weight)
                        myUserID.child("Height").setValue(height)
                        auth.signOut()
                        Toast.makeText(this, "Your account created successfully", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

        }

        btnSignUpReset.setOnClickListener {
            edtSignUpFirstName.setText("")
            edtSignUpAge.setText("")
            edtSignUpEmail.setText("")
            edtSignUpPhone.setText("")
            edtSignUpPassword.setText("")
            edtSignUpLastName.setText("")
            edtSignUpHeight.setText("")
            edtSignUpWeight.setText("")
            edtSignUpTLHeight.error = null
            edtSignUpTLWeight.error = null
            edtSignUpTLPhone.error = null
            edtSignUpTLAge.error = null
            edtSignUpTLPassword.error = null
            edtSignUpTLEmail.error = null
            edtSignUpTLFirstName.error = null
            edtSignUpTLLastName.error = null
        }


    }


}