    package com.kari.akema.ui

    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import androidx.fragment.app.Fragment
    import com.google.android.material.bottomnavigation.BottomNavigationView
    import com.google.android.material.navigation.NavigationBarView
    import com.kari.akema.R

    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
            navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            bottomNavigationViewInitialize()

            // Default fragment when the activity starts
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

                    else ->{

                    }
                }
                true
            }
    }
