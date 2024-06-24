package com.kari.akema.listeners

import com.kari.akema.models.student.Course

interface OnCourseClickListener {
    fun onCourseClick(course: Course)
}