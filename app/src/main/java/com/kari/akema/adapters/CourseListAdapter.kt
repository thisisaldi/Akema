package com.kari.akema.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kari.akema.R
import com.kari.akema.listeners.OnCourseClickListener
import com.kari.akema.models.student.Course

class CourseListAdapter(
    private val courses: List<Course>,
    private val itemClickListener: OnCourseClickListener
) : RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_list, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.bind(course)
        holder.itemView.setOnClickListener {
            itemClickListener.onCourseClick(course)
        }
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mataKuliahTv: TextView = itemView.findViewById(R.id.mata_kuliah)

        fun bind(course: Course) {
            mataKuliahTv.text = course.subject
        }
    }
}
