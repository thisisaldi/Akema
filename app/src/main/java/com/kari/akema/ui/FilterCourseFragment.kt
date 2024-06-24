package com.kari.akema.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kari.akema.R
import com.kari.akema.adapters.CourseListAdapter
import com.kari.akema.listeners.OnCourseClickListener
import com.kari.akema.models.student.Course
import com.kari.akema.services.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FilterCourseFragment : Fragment(), OnCourseClickListener {
    private lateinit var view: View
    private lateinit var courseRv: RecyclerView

    private lateinit var apiClient: ApiClient
    private lateinit var courses: List<Course>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view = inflater.inflate(R.layout.fragment_filter_course, container, false)
        courseRv = view.findViewById(R.id.course_list_rv)
        courseRv.layoutManager = LinearLayoutManager(requireContext())
        val backButton: ImageButton = view.findViewById(R.id.history_back_button)
        backButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                replace(R.id.nav_host_fragment, PresensiFragment())
                addToBackStack(null)
                commit()
            }
        }

        apiClient = ApiClient(requireContext())
        CoroutineScope(Dispatchers.Main).launch {
            fetchCourses()
            courses.let {
                val adapter = CourseListAdapter(it, this@FilterCourseFragment)
                courseRv.adapter = adapter
            }
        }

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

    override fun onCourseClick(course: Course) {
        val bundle = Bundle().apply {
            putString("id", course.id)
        }

        val fragment = HistoryFragment().apply {
            arguments = bundle
        }

        activity?.supportFragmentManager?.beginTransaction()?.apply {
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            replace(R.id.nav_host_fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }
}