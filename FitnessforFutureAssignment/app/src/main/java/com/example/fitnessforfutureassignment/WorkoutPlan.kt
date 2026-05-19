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
import kotlinx.android.synthetic.main.activity_workout_plan.*

class WorkoutPlan : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_plan)

        txtWPBeginner.setOnClickListener{
            val intent = Intent(this, WorkoutMenu::class.java)
            intent.putExtra("Decider",1)
            startActivity(intent)
        }

        txtWPIntermediate.setOnClickListener{
            val intent = Intent(this, WorkoutMenu::class.java)
            intent.putExtra("Decider",2)
            startActivity(intent)
        }

        txtWPAdvanced.setOnClickListener{
            val intent = Intent(this, WorkoutMenu::class.java)
            intent.putExtra("Decider",3)
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
                finish()
                val intent = Intent(this, Signup::class.java)
                startActivity(intent)
            }
            R.id.miCC -> {
                finish()
                val intent = Intent(this,CaloriesCalculator::class.java)
                startActivity(intent)
            }
            R.id.miDP -> {
                finish()
                val intent = Intent(this, DietPlan::class.java)
                startActivity(intent)
            }
            R.id.miWP -> {
                Toast.makeText(this, "You are already in Workout Plan page.", Toast.LENGTH_SHORT)
                    .show()
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