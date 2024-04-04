package com.kari.akema.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kari.akema.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val buttonLogout = view.findViewById<Button>(R.id.logout_button)
        val buttonProfileLengkap = view.findViewById<Button>(R.id.button_profile_lengkap)
        val buttonChangePassword = view.findViewById<Button>(R.id.change_password_button)
        val buttonEditProfile = view.findViewById<Button>(R.id.edit_profile_button)

        buttonLogout.setOnClickListener {
            val message: String? = "Apakah anda ingin keluar?"
            showCustomDialogBox(message)
        }

        buttonProfileLengkap.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileLengkapActivity::class.java))
        }

        buttonChangePassword.setOnClickListener {
            startActivity(Intent(requireContext(), GantiPasswordActivity::class.java))
        }

        buttonEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        return view
    }

    private fun showCustomDialogBox(message: String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.popout_log_out)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val btnLogout: Button = dialog.findViewById(R.id.buttonLogout)
        val btnBatal: Button = dialog.findViewById(R.id.buttonBatal)

        tvMessage.text = message

        btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Anda Berhasil Logout!", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        btnBatal.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
