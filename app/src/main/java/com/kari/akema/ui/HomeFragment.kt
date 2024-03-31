package com.kari.akema.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.kari.akema.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi tombol dan bottom navigation view
        val presenceButton: Button = view.findViewById(R.id.presence_button)
        val historyPresenceButton: TextView = view.findViewById(R.id.presence_history_button)

        presenceButton.setOnClickListener {
            val bottomSheetFragment = PresenceFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
        historyPresenceButton.setOnClickListener {
            val historyFragment = HistoryFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                ?.replace(R.id.nav_host_fragment, historyFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        return view
    }
}
