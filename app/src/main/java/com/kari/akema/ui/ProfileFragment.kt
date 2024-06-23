package com.kari.akema.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kari.akema.R
import com.kari.akema.models.auth.LogoutResponse
import com.kari.akema.services.ApiClient
import com.kari.akema.services.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var view: View
    private lateinit var sessionManager: SessionManager

    private lateinit var apiClient: ApiClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false)

        val buttonLogout = view.findViewById<Button>(R.id.logout_button)
        val buttonProfileLengkap = view.findViewById<Button>(R.id.button_profile_lengkap)
        val buttonChangePassword = view.findViewById<Button>(R.id.change_password_button)
        val buttonEditProfile = view.findViewById<Button>(R.id.edit_profile_button)
        val nameTv = view.findViewById<TextView>(R.id.full_name)

        apiClient = ApiClient(requireContext())
        sessionManager = SessionManager(requireContext())
        val studentData: HashMap<String, String> = sessionManager.getStudentDetails()

        nameTv.text = studentData["name"]

        buttonLogout.setOnClickListener {
            val message: String = "Apakah anda ingin keluar?"
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
            apiClient.getApiService().logout()
                .enqueue(object : Callback<LogoutResponse> {
                    override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                        Log.d("logout", "FAILED!!!!")
                        Log.d("logout", t.toString())
                    }

                    override fun onResponse(
                        call: Call<LogoutResponse>,
                        response: Response<LogoutResponse>
                    ) {
                        val loginResponse = response.body()
                        Log.d("logout", loginResponse.toString())
                        if (!response.isSuccessful) {
                            Log.e("logout", "Failed to retrieve token from cookies")
                            return
                        }
                        Log.d("logout", "Token deleted")
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        sessionManager.clear()
                        startActivity(intent)
                    }
                })
        }

        btnBatal.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }
}
