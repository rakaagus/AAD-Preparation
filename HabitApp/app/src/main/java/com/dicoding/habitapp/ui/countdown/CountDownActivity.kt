package com.dicoding.habitapp.ui.countdown

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.notification.NotificationWorker
import com.dicoding.habitapp.utils.HABIT
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import java.util.concurrent.TimeUnit

class CountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        supportActionBar?.title = "Count Down"

        val habit = getParcelableExtra(intent, HABIT, Habit::class.java)

        if (habit != null){
            findViewById<TextView>(R.id.tv_count_down_title).text = habit.title

            val viewModel = ViewModelProvider(this).get(CountDownViewModel::class.java)

            //TODO 10 : Set initial time and observe current time. Update button state when countdown is finished
            val tvCountDown = findViewById<TextView>(R.id.tv_count_down)
            viewModel.setInitialTime(habit.minutesFocus)
            viewModel.currentTimeString.observe(this){
                tvCountDown.text = it
            }

            //TODO 13 : Start and cancel One Time Request WorkManager to notify when time is up.
            viewModel.eventCountDownFinish.observe(this){
                val data = workDataOf(HABIT_ID to habit.id, HABIT_TITLE to habit.title)
                val time = viewModel.getTime()

                val builder = OneTimeWorkRequestBuilder<NotificationWorker>().setInputData(data)
                    .setInitialDelay(time!!, TimeUnit.MILLISECONDS).build()
                WorkManager.getInstance(this).enqueue(builder)
            }

            findViewById<Button>(R.id.btn_start).setOnClickListener {
                viewModel.startTimer()
                updateButtonState(true)
            }

            findViewById<Button>(R.id.btn_stop).setOnClickListener {
                viewModel.resetTimer()
                val workManager = WorkManager.getInstance(this)
                workManager.cancelAllWork()
                updateButtonState(false)
            }
        }

    }

    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = !isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = isRunning
    }
}