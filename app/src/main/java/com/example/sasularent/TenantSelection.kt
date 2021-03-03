package com.example.sasularent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TenantSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_selection)

        val findAHouse = findViewById<Button>(R.id.find_a_house_for_rent)
        val goPay = findViewById<Button>(R.id.go_to_pay)

        findAHouse.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        goPay.setOnClickListener {
            startActivity(Intent(this, Payment::class.java))
        }
    }
}