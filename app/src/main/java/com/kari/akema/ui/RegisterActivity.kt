package com.kari.akema.ui

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButtonToggleGroup
import com.kari.akema.R

class RegisterActivity : AppCompatActivity() {

    private val registerMahasiswaFragment = RegisterMahasiswaFragment()
    private val registerDosenFragment = RegisterDosenFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        selectFragment(registerMahasiswaFragment)
        displayLoginInitialize()
        listenRegisterButton()

        val toggleButtonGroup : MaterialButtonToggleGroup = findViewById(R.id.toggleButtonRegister)
        toggleButtonGroup.addOnButtonCheckedListener { group, _, _ ->
            val selectedButtonId = group.checkedButtonId
            when (selectedButtonId) {
                R.id.btn_register_mahasiswa -> {
                    selectFragment(registerMahasiswaFragment)
                }
                R.id.btn_register_dosen -> {
                    selectFragment(registerDosenFragment)
                }
            }
        }
    }

    private fun displayLoginInitialize() {
        val tvRegister : TextView = findViewById(R.id.tv_redirect_login)
        val text = SpannableStringBuilder("Sudah punya akun? Masuk")
        val color = ResourcesCompat.getColor(getResources(), R.color.orange, null)

        text.setSpan(
            ForegroundColorSpan(color),
            18, text.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        val clickSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        text.setSpan(clickSpan,
            18, text.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        tvRegister.movementMethod = LinkMovementMethod.getInstance()
        tvRegister.text = text
    }

    private fun listenRegisterButton() {
        val btnLogin : Button = findViewById(R.id.btn_submit_register)
        btnLogin.setOnClickListener{
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun selectFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_register, fragment)
            .commit()
    }
}

