package com.kari.akema.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.kari.akema.R

class GantiPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ganti_password)

        // Button Update Password
        val buttonPerbarui = findViewById<Button>(R.id.button_perbarui)
        buttonPerbarui.setOnClickListener {
            Toast.makeText(applicationContext, "Password Berhasil Diganti!", Toast.LENGTH_LONG).show()
        }

        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}