package com.example.fitnessforfutureassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_diet_plan.*
import java.time.LocalDate
import java.util.*

class DietPlan : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var cal: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)
        var myRef = FirebaseDatabase.getInstance().reference
        var Uid = Firebase.auth.currentUser?.uid.toString()
        var totalBC = 0
        var totalLC = 0
        var totalDC = 0
        var totalC = 0
        cal = Calendar.getInstance()
        var dayOfWeek =
            cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).toString()
        var date = LocalDate.now().toString()
        txtDPDD.setText("${dayOfWeek} (${date})")
        if (dayOfWeek.equals("Monday")) {
            txtDPBreakfast.setText("2 Eggs\n200ml of Milk\n2 pieces of Bread")
            totalBC += 663
            txtDPBreakfastC.setText(totalBC.toString())
            txtDPLunch.setText("one bowl of Coconut Chicken with Sweet Potato")
            totalLC += 291
            txtDPLunchC.setText(totalLC.toString())
            txtDPDinner.setText("Grilled Steak with Broccoli and 1/2 cup of rice")
            totalDC += 659
            txtDPDinnerC.setText(totalDC.toString())
        } else if (dayOfWeek.equals("Tuesday")) {
            txtDPBreakfast.setText("2 servings Baked Banana-Nut Oatmeal Cups\n1 clementine")
            totalBC += 387
            txtDPBreakfastC.setText(totalBC.toString())
            txtDPLunch.setText("1 serving Veggie & Hummus Sandwich")
            totalLC += 325
            txtDPLunchC.setText(totalLC.toString())
            txtDPDinner.setText("1 serving Sheet-Pan Chicken Fajita Bowls with 1/3 cup cooked brown rice")
            totalDC += 507
            txtDPDinnerC.setText(totalDC.toString())
        } else if (dayOfWeek.equals("Wednesday")) {
            txtDPBreakfast.setText("2 servings Baked Banana-Nut Oatmeal Cups\n1 clementine")
            totalBC += 387
            txtDPBreakfastC.setText(totalBC.toString())
            txtDPLunch.setText("1 serving Chipotle-Lime Cauliflower Taco Bowls")
            totalLC += 344
            txtDPLunchC.setText(totalLC.toString())
            txtDPDinner.setText("1 serving Zucchini-Chickpea Veggie Burgers with Tahini-Ranch Sauce\n1 serving Oven Sweet-Potato Fries")
            totalDC += 495
            txtDPDinnerC.setText(totalDC.toString())
        } else if (dayOfWeek.equals("Thursday")) {
            txtDPBreakfast.setText("2 servings Baked Banana-Nut Oatmeal Cups\n1 clementine")
            totalBC += 387
            txtDPBreakfastC.setText(totalBC.toString())
            txtDPLunch.setText("1 serving Chipotle-Lime Cauliflower Taco Bowls")
            totalLC += 344
            txtDPLunchC.setText(totalLC.toString())
            txtDPDinner.setText("1 serving Easy Salmon Cakes over 2 cups baby spinach\n1 (2-inch) piece whole-wheat baguette")
            totalDC += 475
            txtDPDinnerC.setText(totalDC.toString())
        } else if (dayOfWeek.equals("Friday")) {
            txtDPBreakfast.setText("1 serving Muesli with Raspberries\n1 medium banana")
            totalBC += 393
            txtDPBreakfastC.setText(totalBC.toString())
            txtDPLunch.setText("1 serving Chipotle-Lime Cauliflower Taco Bowls")
            totalLC += 344
            txtDPLunchC.setText(totalLC.toString())
            txtDPDinner.setText("1 serving Chicken & Cucumber Lettuce Wraps with Peanut Sauce")
            totalDC += 521
            txtDPDinnerC.setText(totalDC.toString())
        } else if (dayOfWeek.equals("Saturday")) {
            txtDPBreakfast.setText("1 serving Muesli with Raspberries")
            totalBC += 287
            txtDPBreakfastC.setText(totalBC.toString())
            txtDPLunch.setText("1 serving Chipotle-Lime Cauliflower Taco Bowls")
            totalLC += 344
            txtDPLunchC.setText(totalLC.toString())
            txtDPDinner.setText("1 serving Mediterranean Ravioli with Artichokes & Olives")
            totalDC += 454
            txtDPDinnerC.setText(totalDC.toString())
        } else if (dayOfWeek.equals("Sunday")) {
            txtDPBreakfast.setText("1 serving \"Egg in a Hole\" Peppers with Avocado Salsa")
            totalBC += 285
            txtDPBreakfastC.setText(totalBC.toString())
            txtDPLunch.setText("1 serving Curried Sweet Potato & Peanut Soup")
            totalLC += 345
            txtDPLunchC.setText(totalLC.toString())
            txtDPDinner.setText("1 1/2 serving Spinach & Artichoke Dip Pasta")
            totalDC += 556
            txtDPDinnerC.setText(totalDC.toString())
        }
        totalC += totalBC + totalDC + totalLC
        myRef.child("Data&Date").child(Uid).child(date).setValue(totalC)
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
                val intent = Intent(this, CaloriesCalculator::class.java)
                startActivity(intent)
            }
            R.id.miDP -> {
                Toast.makeText(this, "You are already in Diet Plan page.", Toast.LENGTH_SHORT)
                    .show()
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
