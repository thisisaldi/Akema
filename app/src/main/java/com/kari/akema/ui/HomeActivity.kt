package com.kari.akema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fragmentManager = supportFragmentManager

        val presenceHistoryButton: TextView = findViewById(R.id.presence_history_button)
        presenceHistoryButton.setOnClickListener(this)

        popupPresenceButtonInitialize()
        bottomNavigationViewInitialize()
    }

    private fun bottomNavigationViewInitialize() {

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.menu_presence
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
        bottomNavigationView.itemIconTintList = null
        bottomNavigationView.backgroundTintList = null
    }

    private fun popupPresenceButtonInitialize() {
        Log.d("Fragment Debug", "Finding presence_button")
        val presenceButton: Button = findViewById(R.id.presence_button)
        presenceButton.setOnClickListener {
            val bottomSheetFragment = PresenceFragment()
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.tag)
        }
        Log.d("Fragment Debug", "Popup Presence Button Initialized")
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.presence_history_button -> {
                val historyIntent = Intent(this@HomeActivity, HistoryActivity::class.java)
                startActivity(historyIntent)
            }
        }
    }

}