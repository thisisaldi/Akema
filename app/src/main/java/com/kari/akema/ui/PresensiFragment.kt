package com.kari.akema.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kari.akema.R
import com.kari.akema.adapters.CourseAdapter
import com.kari.akema.models.Course
import com.kari.akema.services.ApiClient
import com.kari.akema.services.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class PresensiFragment : Fragment() {
    private lateinit var view: View
    private lateinit var nameTv: TextView
    private lateinit var nimTv: TextView
    private lateinit var recyclerView: RecyclerView

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private lateinit var courses: List<Course>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_presensi, container, false)

        nameTv = view.findViewById(R.id.text_name)
        nimTv = view.findViewById(R.id.text_nim)
        recyclerView = view.findViewById<RecyclerView>(R.id.rv_courses)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        apiClient = ApiClient(requireContext())
        sessionManager = SessionManager(requireContext())

        val studentData: HashMap<String, String> = sessionManager.getStudentDetails()

        nameTv.text = studentData["name"]
        nimTv.text = studentData["nim"]

        CoroutineScope(Dispatchers.Main).launch {
            val adapter = CourseAdapter(getTodayCourses())
            recyclerView.adapter = adapter
        }
        initializeOnClickListener()

        return view
    }

    private suspend fun fetchCourses() {
        try {
            val response = apiClient.getApiService().getStudent()
            if (!response.isSuccessful) {
                Log.e("student_data", "Error: ${response.message()}")
                return
            }
            response.body()?.let {
                courses = it.data.courses
            } ?: run {
                Log.e("student_data", "Response body is null")
            }
        } catch (t: Throwable) {
            Log.e("student_data", "FAILED!!!!")
            Log.e("student_data", t.toString())
        }
    }

    private fun getCurrentDay(): String {
        val daysArray =
            arrayOf("Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")
        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)
        return daysArray[day]
    }

    private suspend fun getTodayCourses(): List<Course> {
        fetchCourses()
        val dayOfWeek: String = getCurrentDay()
        return courses.filter { it.day == dayOfWeek }
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
