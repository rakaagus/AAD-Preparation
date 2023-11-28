package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter.convertMillisToString
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity(){

    private lateinit var tasksTitleEditText : TextInputEditText
    private lateinit var tasksDesciptionEditText : TextInputEditText
    private lateinit var tasksDateEditText : TextInputEditText
    private lateinit var btnDeleteTasks : Button

    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val taskId = intent.getIntExtra(TASK_ID, 0)
        Log.d("TAG", "onCreate: ${taskId}")

        //TODO 11 : Show detail task and implement delete action
        tasksTitleEditText = findViewById(R.id.detail_ed_title)
        tasksDesciptionEditText = findViewById(R.id.detail_ed_description)
        tasksDateEditText = findViewById(R.id.detail_ed_due_date)
        btnDeleteTasks = findViewById(R.id.btn_delete_task)

        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        detailTaskViewModel.setTaskId(taskId)
        detailTaskViewModel.task.observe(this){
            it?.let { data->
                setUpDetailTasks(data)
            }
        }
    }

    private fun setUpDetailTasks(it: Task) {
        tasksTitleEditText.setText(it.title)
        tasksDesciptionEditText.setText(it.description)
        tasksDateEditText.setText(convertMillisToString(it.dueDateMillis))
        btnDeleteTasks.setOnClickListener {
            detailTaskViewModel.deleteTask()
            finish()
        }
    }
}