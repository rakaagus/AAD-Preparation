package com.dicoding.courseschedule.ui.add

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var addCourseViewModel: AddCourseViewModel

    private lateinit var courseName: TextInputEditText
    private lateinit var courseLecture: TextInputEditText
    private lateinit var courseNote: TextInputEditText
    private lateinit var courseDay: Spinner
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = resources.getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }

    private fun init(){

        val factory = AddCourseViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        courseName = findViewById(R.id.ed_course_name)
        courseLecture = findViewById(R.id.ed_lecturer)
        courseNote = findViewById(R.id.ed_note)
        courseDay = findViewById(R.id.spinner_day)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)
    }

    fun showStartTime(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "startTime")
    }

    fun showEndTime(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "endTime")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_insert -> {
                val nameCourse = courseName.text.toString().trim()
                val lectureCourse = courseLecture.text.toString().trim()
                val dayCourse = courseDay.selectedItemPosition
                val noteCourse = courseNote.text.toString().trim()
                val startTime = tvStartTime.text.toString().trim()
                val endTime = tvEndTime.text.toString().trim()

                if(nameCourse.isNotEmpty() && lectureCourse.isNotEmpty() && noteCourse.isNotEmpty()){
                    addCourseViewModel.insertCourse(
                        nameCourse,
                        dayCourse,
                        startTime,
                        endTime,
                        lectureCourse,
                        noteCourse
                    )
                    finish()
                }else {
                    Toast.makeText(this, getString(R.string.empty_list_message), Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when(tag){
            "startTime" -> tvStartTime.text = timeFormat.format(calendar.time)
            "endTime" -> tvEndTime.text = timeFormat.format(calendar.time)
        }
    }
}