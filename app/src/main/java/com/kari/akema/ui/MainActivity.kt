package com.kari.akema.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.kari.akema.R
import com.kari.akema.models.StudentDataResponse
import com.kari.akema.services.ApiClient
import com.kari.akema.services.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiClient = ApiClient(this)
        sessionManager = SessionManager(this)

        fetchStudentData()

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        bottomNavigationViewInitialize()
        replaceFragment(PresensiFragment())
    }

    private fun bottomNavigationViewInitialize() {

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.menu_presence
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.backgroundTintList = null
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                // R.id.menu_home -> replaceFragment(HomeFragment())
                R.id.menu_presence -> replaceFragment(PresensiFragment())
                R.id.menu_profile -> replaceFragment(ProfileFragment())

                else -> {

                }
            }
            true
        }

    private fun fetchStudentData() {
        apiClient.getApiService().getStudents()
            .enqueue(object : Callback<StudentDataResponse> {
                override fun onFailure(call: Call<StudentDataResponse>, t: Throwable) {
                    Log.d("student_data", "FAILED!!!!")
                    Log.d("student_data", t.toString())
                }

                override fun onResponse(
                    call: Call<StudentDataResponse>,
                    response: Response<StudentDataResponse>
                ) {
                    Log.d("student_data", response.toString())
                    Log.d("student_data", response.body().toString())

                    if (response.isSuccessful) {
                        val studentDataResponse = response.body()
                        if (studentDataResponse != null) {
                            sessionManager.setStudentDetails(studentDataResponse)
                        }
                    } else {
                        Log.d("student_data", "Error: ${response.message()}")
                    }
                }
            })
    }
}
