package com.example.fitnessforfutureassignment

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_workout_menu.*

class WorkoutMenu : AppCompatActivity() {
    lateinit var auth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth

        var decider = intent.getIntExtra("Decider",0)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_menu)


        if (decider == 1){
            textView1.isVisible = true
            textView1.setOnClickListener {
                val openBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themanual.com/fitness/best-workouts-for-beginners/#dt-heading-best-strengthening-workouts-for-beginners"))
                startActivity(openBrowser)
            }
        }else if (decider == 2){
            textView2.isVisible = true
            textView2.setOnClickListener {
                val openBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/nxisr1AalNc"))
                startActivity(openBrowser)
            }
        }else if(decider == 3){
            textView3.isVisible = true
            textView3.setOnClickListener {
                val openBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("https://1upnutrition.com/blogs/training/full-body-workout-without-weights"))
                startActivity(openBrowser)
            }
        }

        btnWPBack.setOnClickListener {
            finish()
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