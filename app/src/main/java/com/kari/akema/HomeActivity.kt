package com.kari.akema

import PresenceFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        popupPresenceButtonInitialize()
        bottomNavigationViewInitialize()
    }

    private fun bottomNavigationViewInitialize() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.backgroundTintList = null
    }

    private fun popupPresenceButtonInitialize() {
        val presenceButton: Button = findViewById(R.id.presence_button)
        presenceButton.setOnClickListener {
            val bottomSheetFragment = PresenceFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}