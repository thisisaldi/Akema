package com.kari.akema.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.kari.akema.R

class NotificationFragment : Fragment() {
    private lateinit var view: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false)
        initializeBackButton()
        return view
    }

    private fun initializeBackButton() {
        val backButton: ImageButton = view.findViewById(R.id.notification_back_button)
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                replace(R.id.nav_host_fragment, PresensiFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

}