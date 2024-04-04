package com.kari.akema.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kari.akema.R

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)

        // Button Update Password
        val buttonPerbarui = findViewById<Button>(R.id.button_perbarui)
        buttonPerbarui.setOnClickListener {
            Toast.makeText(applicationContext, "Profile Berhasil Diperbaharui!", Toast.LENGTH_LONG).show()
        }

        // Tombol Back
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}
