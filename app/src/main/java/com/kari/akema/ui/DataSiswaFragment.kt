package com.kari.akema.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kari.akema.R
import com.kari.akema.services.ApiClient
import com.kari.akema.services.SessionManager

class DataSiswaFragment : Fragment() {
    private lateinit var rootView: View

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_data_siswa, container, false)

        sessionManager = SessionManager(requireContext())
        updateUI()
        return rootView
    }

    private fun updateUI() {
        val nimTv: TextView = rootView.findViewById(R.id.text_nim)
        val angkatan: TextView = rootView.findViewById(R.id.text_angkatan)

        val studentData = sessionManager.getStudentDetails()
        Log.d("student_data_view", studentData.toString())
        nimTv.text = studentData["nim"]
        angkatan.text = studentData["classOf"]
    }
}