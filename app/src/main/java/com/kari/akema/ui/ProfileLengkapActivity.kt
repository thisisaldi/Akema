package com.kari.akema.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kari.akema.R

class ProfileLengkapActivity : AppCompatActivity() {

    private val pribadiFragment = PribadiFragment()
    private val dataSiswaFragment = DataSiswaFragment()

    private lateinit var pribadiBtn: Button
    private lateinit var datasiswaBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_lengkap)

        pribadiBtn = findViewById(R.id.pribadiBtn)
        datasiswaBtn = findViewById(R.id.datasiswaBtn)

        // Set listeners for buttons
        pribadiBtn.setOnClickListener {
            selectFragment(pribadiFragment)
        }

        datasiswaBtn.setOnClickListener {
            selectFragment(dataSiswaFragment)
        }

        // Set initial appearance for buttons and select default fragment
        selectFragment(pribadiFragment)

        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun selectFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, fragment)
            commit()
        }

        // Change button appearance based on selection
        pribadiBtn.setBackgroundColor(if (fragment is PribadiFragment) Color.parseColor("#E67E22") else Color.parseColor("#DFE6E9"))
        pribadiBtn.setTextColor(if (fragment is PribadiFragment) Color.WHITE else Color.parseColor("#E67E22"))
        datasiswaBtn.setBackgroundColor(if (fragment is DataSiswaFragment) Color.parseColor("#E67E22") else Color.parseColor("#DFE6E9"))
        datasiswaBtn.setTextColor(if (fragment is DataSiswaFragment) Color.WHITE else Color.parseColor("#E67E22"))
    }
}
