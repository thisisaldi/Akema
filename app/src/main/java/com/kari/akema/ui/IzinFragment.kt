package com.kari.akema.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.common.util.concurrent.ListenableFuture
import com.kari.akema.R
import com.kari.akema.adapter.HintAdapter

class IzinFragment : Fragment() {
    private lateinit var view: View
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        view = inflater.inflate(R.layout.fragment_izin, container, false)
        initializeBackButton()
        initializeSpinner()

        return view
    }

    private fun initializeSpinner() {
        val jenisIzinSpinner: Spinner = view.findViewById(R.id.jenis_izin_spinner)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.jenis_izin,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        jenisIzinSpinner.adapter = adapter
    }

    private fun initializeBackButton() {
        val backButton: ImageButton = view.findViewById(R.id.izin_back_button)
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                replace(R.id.nav_host_fragment, HomeFragment())
                addToBackStack(null)
                commit()
            }
        }
    }

}