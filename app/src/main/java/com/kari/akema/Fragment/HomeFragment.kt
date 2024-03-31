package com.kari.akema.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.kari.akema.PresenceFragment
import com.kari.akema.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi tombol dan bottom navigation view
        val presenceButton: Button = view.findViewById(R.id.presence_button)

        presenceButton.setOnClickListener {
            val bottomSheetFragment = PresenceFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        return view
    }
}
