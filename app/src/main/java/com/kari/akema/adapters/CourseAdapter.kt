package com.kari.akema.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kari.akema.R
import com.kari.akema.models.student.Course

class CourseAdapter(private val courses: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_card, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]

        holder.course.text = course.subject
        holder.startTime.text = course.starttime
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var startTime: TextView = itemView.findViewById(R.id.start_text)
        var course: TextView = itemView.findViewById(R.id.course_text)
    }
}