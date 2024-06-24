package com.kari.akema.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kari.akema.R
import com.kari.akema.listeners.OnCourseClickListener
import com.kari.akema.models.presence.Attendance
import com.kari.akema.models.student.Course
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryListAdapter(
    private val attendances: List<Attendance>
) : RecyclerView.Adapter<HistoryListAdapter.AttendanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false)
        return AttendanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val attendance = attendances[position]
        holder.statusTv.text = attendance.status.split(" ").joinToString(" ") { it.capitalize() }
        holder.timeTv.text = convertDateFormat(attendance.timestamp)
    }

    override fun getItemCount(): Int {
        return attendances.size
    }

    fun convertDateFormat(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat(
            "EEEE, dd MMMM yyyy",
            Locale("id", "ID")
        )

        val date = inputFormat.parse(dateString)

        return outputFormat.format(date!!)
    }

    inner class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val statusTv: TextView = itemView.findViewById(R.id.status_text)
        val timeTv: TextView = itemView.findViewById(R.id.hari_tanggal)
    }
}
