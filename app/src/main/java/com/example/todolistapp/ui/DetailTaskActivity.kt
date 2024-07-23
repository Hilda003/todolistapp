package com.example.todolistapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.example.todolistapp.R
import com.example.todolistapp.ViewModelFactory
import com.example.todolistapp.databinding.ActivityDetailTaskBinding
import com.example.todolistapp.retrofit.ApiConfig
import com.example.todolistapp.viewmodel.DetailTaskViewModel

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTaskBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)



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
        binding.btnGetQuote.setOnClickListener {
            ApiConfig().getQuotes(this) { quotes ->
                if (quotes.isNotEmpty()) {
                    val quote = quotes[0]
                    binding.quote.text = quote.content
                } else {
                    binding.quote.text = "No quotes found"
                }
            }
        }
        //TODO 11 : Show detail task and implement delete action
    }
    private fun showTaskDetail(task: Task) {
        findViewById<TextView>(R.id.detail_ed_title).text = task.title
        findViewById<TextView>(R.id.detail_ed_description).text = task.description
        findViewById<TextView>(R.id.detail_ed_due_date).text = DateConverter.convertMillisToString(task.dueDateMillis)

    }
}