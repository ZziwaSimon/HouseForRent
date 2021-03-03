package com.example.sasularent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sasularent.model.LandlordInfo
import com.example.sasularent.model.TenantInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register_as_tenant : AppCompatActivity() {
    // Accessing the Real time database
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.reference
    var myFirebaseDatabase: DatabaseReference? = null

    // Creating member variable of FirebaseAuth
    private var mAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_as_tenant)

        val btnRegister_as_tenant = findViewById<Button>(R.id.Registration_tenant)
        btnRegister_as_tenant.setOnClickListener {
            // signUp button in the dialog box
            val fName = findViewById<EditText>(R.id.firstName_tenant).text.toString()
            val lName = findViewById<EditText>(R.id.lastName_tenant).text.toString()
            val email = findViewById<EditText>(R.id.emailAddress_tenant).text.toString()
            //val uName = findViewById<EditText>(R.id.user_name_for_display).text.toString()
            val pNumber = findViewById<EditText>(R.id.phoneNumber_tenant).text.toString()
            //val pwd = findViewById<EditText>(R.id.password_landlord).text.toString()
            //val location = findViewById<EditText>(R.id.editLocation_landlord).toString()
            val cPWD = findViewById<EditText>(R.id.editPassword_tenant).text.toString()

            if (fName.isNotEmpty() && lName.isNotEmpty() && pNumber.isNotEmpty() && email.isNotEmpty() && cPWD.isNotEmpty()) {
                myFirebaseDatabase = database.getReference("tenant")
                mAuth = FirebaseAuth.getInstance()
                mAuth!!.createUserWithEmailAndPassword(email, cPWD)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            mAuth!!.currentUser!!.sendEmailVerification()
                                .addOnCompleteListener(this) {
                                    if (it.isSuccessful) {
                                        // Creating and sending data to an object
                                        Toast.makeText(
                                            applicationContext,
                                            "Verification email sent to $email", Toast.LENGTH_SHORT
                                        ).show()
                                        // getting current user from auth
                                        val user = FirebaseAuth.getInstance().currentUser
                                        // username and email
                                        val userId = user!!.uid
                                        val emailAddress = user.email
                                        //val newUser = UserSignUpInfo(fName,lName,pNumber,emailAddress,pwd,uName,null)
                                        val tenantInfo = TenantInfo(fName,lName,email,pNumber,cPWD)
                                        // Getting reference to the user's node in the database
                                        myRef.child("tenant").child(userId).setValue(tenantInfo)
                                    } else {
                                        Toast.makeText(applicationContext, "Verification email not sent but signup was successful",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(applicationContext, "Not Successful", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(applicationContext, "Passwords don't match", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}