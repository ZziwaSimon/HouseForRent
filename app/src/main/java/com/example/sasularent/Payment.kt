package com.example.sasularent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Payment : AppCompatActivity() {
    // Accessing the Real time database
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.reference
    var myFirebaseDatabase: DatabaseReference? = null

    private var mAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val textBoxID = findViewById<EditText>(R.id.LLd_Ph_No).text.toString()
        val textBoxAmount = findViewById<EditText>(R.id.editAmount).text.toString()
        val textNumber = findViewById<EditText>(R.id.payer_number)
        val buttonPay = findViewById<Button>(R.id.pay_button)

        buttonPay.setOnClickListener {
            // Fetch information about the RentalID
            myRef.child("tenant").child(textBoxID)
            val checkValues = object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()){
                        // Make paying to the landlord
                        // Proceed with the payment
                    }else{
                        // Toast a message
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(applicationContext, "Error occured", Toast.LENGTH_SHORT).show()
                }

            }
            myRef.addValueEventListener(checkValues)
            //myRef.addChildEventListener(checkValues)
        }
    }

    private fun makePayment(){

    }
}