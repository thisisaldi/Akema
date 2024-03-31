package com.kari.akema.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kari.akema.R

class ProfileLengkapActivity : AppCompatActivity() {

    private lateinit var pribadiBtn: Button
    private lateinit var datasiswaBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_lengkap)

        // Inisialisasi button
        pribadiBtn = findViewById(R.id.pribadiBtn)
        datasiswaBtn = findViewById(R.id.datasiswaBtn)

        // Set default button
        setDefaultButton()

        // Set onClickListener untuk button
        pribadiBtn.setOnClickListener {
            selectButton(pribadiBtn)
            replaceFragment(PribadiFragment())
        }

        datasiswaBtn.setOnClickListener {
            selectButton(datasiswaBtn)
            replaceFragment(DataSiswaFragment())
        }
    }

    // Fungsi untuk menetapkan button default (Pribadi) saat pertama kali tampil
    private fun setDefaultButton() {
        selectButton(pribadiBtn)
        replaceFragment(PribadiFragment())
    }

    // Fungsi untuk mengganti fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }

    // Fungsi untuk menetapkan button yang dipilih
    private fun selectButton(button: Button) {
        // Reset warna button
        resetButtons()

        // Set background drawable sesuai keadaan button
        button.setBackgroundResource(R.drawable.button_background_selected)
        button.setTextColor(Color.WHITE)
    }

    // Fungsi untuk me-reset warna semua button
    private fun resetButtons() {
        pribadiBtn.setBackgroundResource(R.drawable.button_background_unselected)
        pribadiBtn.setTextColor(getColor(R.color.primary))

        datasiswaBtn.setBackgroundResource(R.drawable.button_background_unselected)
        datasiswaBtn.setTextColor(getColor(R.color.primary))
    }
}
