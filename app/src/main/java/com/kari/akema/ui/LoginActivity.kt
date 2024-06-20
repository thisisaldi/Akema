package com.kari.akema.ui

import android.content.Intent
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kari.akema.R
import com.kari.akema.models.LoginRequest
import com.kari.akema.models.LoginResponse
import com.kari.akema.models.StudentDataResponse
import com.kari.akema.services.ApiClient
import com.kari.akema.services.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    private lateinit var nimEt: EditText
    private lateinit var passwordEt: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        nimEt = findViewById(R.id.nim_input)
        passwordEt = findViewById(R.id.password_input)
        apiClient = ApiClient(this)
        sessionManager = SessionManager(this)

        displayTextViewInitialize()
        displayRegisterInitialize()
        listenLoginButton()
    }

    private fun displayTextViewInitialize() {
        val tvTitle : TextView = findViewById(R.id.tv_title)
        val text = SpannableStringBuilder("Selamat Datang 👋 \ndi Akema App")
        val color = ResourcesCompat.getColor(getResources(), R.color.orange, null)

        text.setSpan(
            ForegroundColorSpan(color),
            22, text.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        text.setSpan(
            StyleSpan(BOLD),
            22, text.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        tvTitle.text = text
    }

    private fun displayRegisterInitialize() {
        val tvRegister : TextView = findViewById(R.id.tv_redirect_register)
        val text = SpannableStringBuilder("Belum punya akun? Daftar")
        val color = ResourcesCompat.getColor(getResources(), R.color.orange, null)

        text.setSpan(
            ForegroundColorSpan(color),
            18, text.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        val clickSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
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

    private fun listenLoginButton() {
        val btnLogin : Button = findViewById(R.id.btn_submit_login)
        btnLogin.setOnClickListener{
            Log.d("login", "start_login")
            var nimText = nimEt.text.toString()
            Log.d("login", "NIM: $nimText")
            var passwordText = passwordEt.text.toString()
            Log.d("login", "password: $passwordText")
            apiClient.getApiService().login(LoginRequest(nim = nimText, password = passwordText))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("login", "FAILED!!!!")
                        Log.d("login", t.toString())
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val loginResponse = response.body()
                        Log.d("login", loginResponse.toString())
                        if (response.isSuccessful) {
                            val cookieList: List<String> = response.headers().values("Set-Cookie")
                            val token: String = cookieList[0].split(";")[0].split("=")[1]
                            if (token != null) {
                                sessionManager.saveAuthToken(token)
                                Log.d("login", "Token saved: $token")
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Log.e("login", "Failed to retrieve token from cookies")
                                // Handle token retrieval failure
                            }
                        } else {
//                         Error logging in
                        }
                    }
                })
        }
    }



}