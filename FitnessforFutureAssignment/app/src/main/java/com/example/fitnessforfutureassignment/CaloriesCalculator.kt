package com.example.fitnessforfutureassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_calories_calculator.*
import kotlinx.android.synthetic.main.activity_home_page.*
import java.lang.Math.pow
import java.time.LocalDate
import java.util.*
import kotlin.properties.Delegates

class CaloriesCalculator : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var cal: Calendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories_calculator)
        cal = Calendar.getInstance()
        var myRef = FirebaseDatabase.getInstance().reference
        auth = Firebase.auth
        var Uid = auth.currentUser?.uid.toString()
        var dayOfWeek =
            cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).toString()
        var date = LocalDate.now().toString()
        var getData = object : ValueEventListener {
            var height = 0
            var weight = 0
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ItemL1 in snapshot.children) {
                    if (ItemL1.key.equals("Data&Date")) {
                        for (ItemL2 in ItemL1.children) {
                            if (ItemL2.key.equals(Uid)) {
                                for (ItemL3 in ItemL2.children) {
                                    if (ItemL3.key.equals(date)) {
                                        txtTotalCal.setText(ItemL3.getValue().toString())
                                    }
                                }
                            }
                        }
                    }
                    if (ItemL1.key.equals("Users&Info")) {
                        for (ItemL2 in ItemL1.children) {
                            if (ItemL2.key.equals(Uid)) {
                                for (ItemL3 in ItemL2.children) {
                                    if (ItemL3.key.equals("Height")) {
                                        txtHeight.setText("\t${ItemL3.getValue().toString()}")
                                        height += ItemL3.getValue().toString().trim().toInt()
                                    } else if (ItemL3.key.equals("Weight")) {
                                        txtWeight.setText("\t${ItemL3.getValue().toString()}")
                                        weight += ItemL3.getValue().toString().trim().toInt()
                                        txtBMI.setText((weight/(pow(2.0,height/100.toDouble()))).toInt().toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        txtPCC.setText("${dayOfWeek} (${date})")
        myRef.addValueEventListener(getData)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        auth = Firebase.auth
        when (item.itemId) {
            R.id.miSignup -> {
                finish()
                val intent = Intent(this, Signup::class.java)
                startActivity(intent)
            }
            R.id.miCC -> {
                Toast.makeText(
                    this,
                    "You are already in Calories Calculator page.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            R.id.miDP -> {
                finish()
                val intent = Intent(this, DietPlan::class.java)
                startActivity(intent)
            }
            R.id.miWP -> {
                finish()
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