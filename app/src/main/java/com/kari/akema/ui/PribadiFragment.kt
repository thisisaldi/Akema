package com.kari.akema.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kari.akema.R
import com.kari.akema.models.LoginRequest
import com.kari.akema.models.LoginResponse
import com.kari.akema.models.Student
import com.kari.akema.models.StudentDataResponse
import com.kari.akema.services.ApiClient
import com.kari.akema.services.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PribadiFragment : Fragment() {
    private lateinit var rootView: View

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_pribadi, container, false)
        sessionManager = SessionManager(requireContext())
        updateUI()
        return rootView
    }


    private fun updateUI() {
        val namaLengkapTv: TextView = rootView.findViewById(R.id.text_nama_lengkap)
        val emailTv: TextView = rootView.findViewById(R.id.text_email)
        val alamatTv: TextView = rootView.findViewById(R.id.text_alamat)

        val studentData = sessionManager.getStudentDetails()
        Log.d("student_data_view", studentData.toString())

        namaLengkapTv.text = studentData["name"]
        emailTv.text = studentData["email"]
        alamatTv.text = studentData["address"]
    }
}