package com.example.todolistapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.example.todolistapp.R
import com.example.todolistapp.ViewModelFactory
import com.example.todolistapp.databinding.ActivityDetailTaskBinding
import com.example.todolistapp.viewmodel.DetailTaskViewModel

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val taskById = intent.getIntExtra(TASK_ID, -1)

        val viewModelFactory = ViewModelFactory.getInstance(this)
        val detailTaskViewModel = ViewModelProvider(this, viewModelFactory)[DetailTaskViewModel::class.java]

        detailTaskViewModel.setTaskId(taskById)
        detailTaskViewModel.task.observe(
            this,
            Observer(this::showTaskDetail)
        )

        binding.btnDeleteTask.setOnClickListener {
            detailTaskViewModel.deleteTask()
            finish()
        }

        //TODO 11 : Show detail task and implement delete action
    }
    private fun showTaskDetail(task: Task) {
        findViewById<TextView>(R.id.detail_ed_title).text = task.title
        findViewById<TextView>(R.id.detail_ed_description).text = task.description
        findViewById<TextView>(R.id.detail_ed_due_date).text = DateConverter.convertMillisToString(task.dueDateMillis)

    }
}