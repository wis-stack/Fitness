package com.example.fitnessforfutureassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        var firstName = ""
        var lastName = ""
        var age = 0
        var height = 0
        var weight = 0
        auth = Firebase.auth

        var myRef = FirebaseDatabase.getInstance().reference
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ItemL1 in snapshot.children) {
                    if (ItemL1.key.equals("Users&Info")) {
                        for (ItemL2 in ItemL1.children) {
                            if (ItemL2.key.equals(Firebase.auth.currentUser?.uid.toString())) {
                                for (ItemL3 in ItemL2.children) {
                                    if (ItemL3.key.equals("FirstName")) {
                                        firstName = ItemL3.getValue().toString()
                                    }
                                    if (ItemL3.key.equals("LastName")) {
                                        lastName = ItemL3.getValue().toString()
                                    }
                                    if (ItemL3.key.equals("Age")) {
                                        age = ItemL3.getValue().toString().toInt()
                                    }
                                    if (ItemL3.key.equals("Height")) {
                                        height = ItemL3.getValue().toString().toInt()
                                    }
                                    if (ItemL3.key.equals("Weight")) {
                                        weight = ItemL3.getValue().toString().toInt()
                                    }
                                    txtHPName.setText("${firstName} ${lastName}")
                                    txtHPInfo.setText("Age: ${age}\nHeight: ${height}\nWeight: ${weight}")
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        myRef.addValueEventListener(getData)

        btnHomePageDiet.setOnClickListener {
            val intent = Intent(this, DietPlan::class.java)
            startActivity(intent)
        }

        btnHomePageWorkout.setOnClickListener {
            val intent = Intent(this, WorkoutPlan::class.java)
            startActivity(intent)
        }

        btnHomePageCalculators.setOnClickListener {
            val intent = Intent(this, CaloriesCalculator::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        auth = Firebase.auth
        when (item.itemId) {
            R.id.miSignup -> {
                val intent = Intent(this, Signup::class.java)
                startActivity(intent)
            }
            R.id.miCC -> {
                val intent = Intent(this, CaloriesCalculator::class.java)
                startActivity(intent)
            }
            R.id.miDP -> {
                val intent = Intent(this, DietPlan::class.java)
                startActivity(intent)
            }
            R.id.miWP -> {
                val intent = Intent(this, WorkoutPlan::class.java)
                startActivity(intent)
            }
            R.id.miLogout -> {
                auth.signOut()
                if (auth.currentUser == null) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        return true
    }
}