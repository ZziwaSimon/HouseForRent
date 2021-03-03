package com.example.sasularent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sasularent.model.LandlordInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register_as_landlord : AppCompatActivity() {

    // Accessing the Real time database
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.reference
    var myFirebaseDatabase: DatabaseReference? = null

    // Creating member variable of FirebaseAuth
    private var mAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_as_landlord)

        val btnRegister_as_landlord = findViewById<Button>(R.id.Registration_landlord)
        btnRegister_as_landlord.setOnClickListener {
            // signUp button in the dialog box
                val fName = findViewById<EditText>(R.id.firstName_landlord).text.toString()
                val lName = findViewById<EditText>(R.id.lastName_landlord).text.toString()
                val email = findViewById<EditText>(R.id.email_landlord).text.toString()
                //val uName = findViewById<EditText>(R.id.user_name_for_display).text.toString()
                val pNumber =findViewById<EditText>(R.id.phoneNumber_landlord).text.toString()
                val pwd = findViewById<EditText>(R.id.password_landlord).text.toString()
                val location = findViewById<EditText>(R.id.editLocation_landlord).toString()
                val cPWD = findViewById<EditText>(R.id.editPassword_landlord).text.toString()

                if (fName.isNotEmpty() && lName.isNotEmpty() && pNumber.isNotEmpty() && email.isNotEmpty() && location.isNotEmpty()
                    && pwd.isNotEmpty() && cPWD.isNotEmpty()){
                    if(pwd == cPWD){
                        mAuth = FirebaseAuth.getInstance()
                        myFirebaseDatabase = database.getReference("landlord")
                        mAuth!!.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(this){ task ->
                            if (task.isSuccessful){
                                mAuth!!.currentUser!!.sendEmailVerification()
                                    .addOnCompleteListener(this){
                                        if(it.isSuccessful){
                                            // Creating and sending data to an object
                                            Toast.makeText(applicationContext,
                                                "Verification email sent to $email", Toast.LENGTH_SHORT).show()
                                            // getting current user from auth
                                            val user = FirebaseAuth.getInstance().currentUser
                                            // username and email
                                            val userId = user!!.uid
                                            val emailAddress = user.email
                                            //val newUser = UserSignUpInfo(fName,lName,pNumber,emailAddress,pwd,uName,null)
                                            val landlordInfo = LandlordInfo(fName,lName,pNumber,null,email,location,pwd,null)
                                            // Getting reference to the user's node in the database
                                            myRef.child("landlord").child(userId).setValue(landlordInfo)//.addOnSuccessListener {
                                           // }
                                        }else{
                                            Toast.makeText(applicationContext,"Verification email not sent but signup was successful",Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }else{
                                Toast.makeText(applicationContext,"Not Successful", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(applicationContext, "Passwords don't match",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(applicationContext,"One of the information is not provided", Toast.LENGTH_SHORT).show()
                }
            }
        }
}
