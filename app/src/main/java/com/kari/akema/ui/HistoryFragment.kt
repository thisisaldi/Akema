package com.kari.akema.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kari.akema.R
import com.kari.akema.adapters.CourseAdapter
import com.kari.akema.adapters.HistoryListAdapter
import com.kari.akema.models.presence.Attendance
import com.kari.akema.models.presence.AttendanceRequest
import com.kari.akema.models.presence.Category
import com.kari.akema.models.student.Course
import com.kari.akema.services.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {
    private lateinit var view: View
    private lateinit var apiClient: ApiClient

    private lateinit var attendances: List<Attendance>
    private lateinit var categories: List<Category>

    private lateinit var jumlahIzin: TextView
    private lateinit var jumlahHadir: TextView
    private lateinit var jumlahTerlambat: TextView

    private lateinit var historyRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_history, container, false)
        val backButton: ImageButton = view.findViewById(R.id.history_back_button)
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                replace(R.id.nav_host_fragment, FilterCourseFragment())
                addToBackStack(null)
                commit()
            }
        }

        jumlahIzin = view.findViewById(R.id.jumlah_izin)
        jumlahHadir = view.findViewById(R.id.jumlah_hadir)
        jumlahTerlambat = view.findViewById(R.id.jumlah_terlambat)

        historyRv = view.findViewById(R.id.rv_history)
        historyRv.layoutManager = LinearLayoutManager(requireContext())

        apiClient = ApiClient(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            fetchAttendanceData()
            Log.d("attendance", attendances.toString())
            val adapter = HistoryListAdapter(attendances)
            historyRv.adapter = adapter
            var izinCount = 0
            var hadirCount = 0
            var terlambatCount = 0

            for (category in categories) {
                when (category.category) {
                    "izin" -> izinCount = category.count
                    "hadir" -> hadirCount = category.count
                    "terlambat" -> terlambatCount = category.count
                }
            }

            jumlahIzin.text = izinCount.toString()
            jumlahHadir.text = hadirCount.toString()
            jumlahTerlambat.text = terlambatCount.toString()
        }


        return view
    }

    private suspend fun fetchAttendanceData() {
        try {
            val courseId = arguments?.getString("id")
            if (courseId == null) {
                Log.e("attendance", "Course ID is null")
                return
            }
            val response = apiClient.getApiService().getAttendance(courseId)
            Log.d("attendance", response.toString())
            Log.d("attendance", response.body().toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    categories = it.data.category
                    attendances = it.data.attendance
                } ?: Log.e("attendance", "Response body is null")
            } else {
                Log.e("attendance", "Error: ${response.message()}")
            }
        } catch (t: Throwable) {
            Log.e("attendance", "FAILED!!!!")
            Log.e("attendance", t.toString())
        }
    }

}