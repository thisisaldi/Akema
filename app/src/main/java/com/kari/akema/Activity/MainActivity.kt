    package com.kari.akema.Activity

    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import androidx.fragment.app.Fragment
    import com.google.android.material.bottomnavigation.BottomNavigationView
    import com.kari.akema.Fragment.HomeFragment
    import com.kari.akema.Fragment.ProfileFragment
    import com.kari.akema.R

    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
            navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            // Default fragment when the activity starts
            replaceFragment(HomeFragment())
        }

        private fun replaceFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
        }

        private val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_home -> replaceFragment(HomeFragment())
                    R.id.menu_profile -> replaceFragment(ProfileFragment())

                    else ->{

                    }
                }
                true
            }
    }
