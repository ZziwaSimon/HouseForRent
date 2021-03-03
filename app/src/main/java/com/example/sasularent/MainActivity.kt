package com.example.sasularent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    // Accessing the Real time database
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.reference
    var myFirebaseDatabase: DatabaseReference? = null

    // Creating member variable of FirebaseAuth
    var mAuth: FirebaseAuth? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLogin = findViewById<Button>(R.id.login_main)
        buttonLogin.setOnClickListener {
            //Toast.makeText(this,"Thanks",Toast.LENGTH_SHORT).show()
            val mDialog = LayoutInflater.from(this).inflate(R.layout.login_dialog_box,null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialog).setTitle("Login Form")
            // showing dialog
            val mAlertDialog = mBuilder.show()
            // Login button click of custom layout
            mDialog.findViewById<Button>(R.id.login_button).setOnClickListener {
                // Getting data
                val email = mDialog.findViewById<EditText>(R.id.username).text.toString()
                val password = mDialog.findViewById<EditText>(R.id.password).text.toString()
                if(email.isNotEmpty() && password.isNotEmpty()){
                    mAuth = FirebaseAuth.getInstance()
                    mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                        val user = mAuth!!.currentUser
                        val uid = user!!.uid
                        if(it.isSuccessful){
                            Toast.makeText(this,"Check this", Toast.LENGTH_SHORT).show()
                            val myLoginInfo = myRef.child("landlord").child(uid)
                            val checkExistence = object:ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if(snapshot.exists()){
                                        Toast.makeText(applicationContext, "Successful",Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this@MainActivity,LandLordView::class.java))
                                    }else{
                                        startActivity(Intent(this@MainActivity, TenantSelection::class.java))
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                   Toast.makeText(applicationContext,"Sorry",Toast.LENGTH_SHORT).show()
                                }

                            }
                            myLoginInfo.addValueEventListener(checkExistence)
                            myLoginInfo.addListenerForSingleValueEvent(checkExistence)
                        }else{
                            Toast.makeText(applicationContext, it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(applicationContext, "Enter all details", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val buttonRegister = findViewById<Button>(R.id.Registration_main)
        buttonRegister.setOnClickListener {
            val mDialog = LayoutInflater.from(this).inflate(R.layout.choose_category,null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialog).setTitle("Choose Category")
            val dialogBox = mBuilder.show()

            mDialog.findViewById<Button>(R.id.as_tenant).setOnClickListener {
                startActivity(Intent(this,Register_as_tenant::class.java))
            }

            mDialog.findViewById<Button>(R.id.as_landlord).setOnClickListener {
                startActivity(Intent(this,Register_as_landlord::class.java))
            }
        }
    }
}