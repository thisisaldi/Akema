package com.kari.akema

import android.app.ActivityOptions
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class HistoryActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val backButton: ImageButton = findViewById(R.id.history_back_button)
        backButton.setOnClickListener(this)

        val calendarView: CalendarView = findViewById(R.id.presence_history_calendar)
        calendarView.date = System.currentTimeMillis()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.history_back_button -> {
                val backIntent = Intent(this@HistoryActivity, HomeActivity::class.java)
                val options = ActivityOptions.makeCustomAnimation(this@HistoryActivity, R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(backIntent, options.toBundle());
            }
        }
    }
}