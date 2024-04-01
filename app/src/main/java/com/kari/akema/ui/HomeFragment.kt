package com.kari.akema.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kari.akema.R

class HomeFragment : Fragment() {
    private lateinit var view: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_home, container, false)
        initializeOnClickListener()

        return view
    }

    private fun initializeOnClickListener() {
        val presenceButton: Button = view.findViewById(R.id.presence_button)
        val historyPresenceButton: TextView = view.findViewById(R.id.presence_history_button)
        val izinButton: Button = view.findViewById(R.id.izin_button)
        val notificationButton: ImageButton = view.findViewById(R.id.notification_button)

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

        izinButton.setOnClickListener {
            val izinFragment = IzinFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                ?.replace(R.id.nav_host_fragment, izinFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        notificationButton.setOnClickListener {
            val notificationFragment = NotificationFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                ?.replace(R.id.nav_host_fragment, notificationFragment)
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}
