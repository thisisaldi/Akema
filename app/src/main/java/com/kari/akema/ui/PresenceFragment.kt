package com.kari.akema.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kari.akema.R
import com.kari.akema.models.student.Course
import com.kari.akema.models.presence.PresentRequest
import com.kari.akema.models.presence.PresentResponse
import com.kari.akema.services.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresenceFragment : BottomSheetDialogFragment() {
    private lateinit var apiClient: ApiClient
    private lateinit var izinButton: Button
    private lateinit var hadirButton: Button

    companion object {
        private const val CODE = "code"
        private const val COURSE = "mata_kuliah"
        private const val DATE = "tanggal_masuk"
        private const val TIME = "jam_masuk"

        fun newInstance(course: Course, date: String): PresenceFragment {
            val fragment = PresenceFragment()
            val args = Bundle()
            args.putString(CODE, course.id)
            args.putString(COURSE, course.subject)
            args.putString(DATE, date)
            args.putString(TIME, course.starttime)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        apiClient = ApiClient(requireContext())

        val course: String? = arguments?.getString(COURSE)
        val date: String? = arguments?.getString(DATE)
        val time: String? = arguments?.getString(TIME)
        val view: View = inflater.inflate(R.layout.fragment_presence, container, false)

        val mkTv: TextView = view.findViewById(R.id.text_mata_kuliah)
        val dateTv: TextView = view.findViewById(R.id.text_tanggal_masuk)
        val timeTv: TextView = view.findViewById(R.id.text_jam_masuk)

        izinButton = view.findViewById(R.id.izin_button)
        hadirButton = view.findViewById(R.id.presence_button)


        izinButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                handleAttendance(izin = true)
            }
        }

        hadirButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                handleAttendance(izin = false)
            }
        }

        mkTv.text = course
        dateTv.text = date
        timeTv.text = time

        return view
    }

    private suspend fun handleAttendance(izin: Boolean): PresentResponse? {
        return try {
            val response = arguments?.getString(CODE)?.let { PresentRequest(code = it, izin = izin) }?.let {
                apiClient.getApiService().present(it)
            }
            Log.d("present", response.toString())
            response
        } catch (e: Exception) {
            Log.d("present", "FAILED!!!!")
            Log.d("present", e.toString())
            null
        } finally {
            Log.d("present", "Fragment dismissed")
            dismiss()
        }
    }
}
