package com.example.todolistapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.utils.DatePickerFragment
import com.example.todolistapp.R
import com.example.todolistapp.ViewModelFactory
import com.example.todolistapp.databinding.ActivityAddTaskBinding
import com.example.todolistapp.viewmodel.AddTaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTaskActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var addTaskViewModel: AddTaskViewModel
    private var dueDateMillis: Long = System.currentTimeMillis()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title = getString(R.string.add_task)

        val viewModelFactory = ViewModelFactory.getInstance(this)
        addTaskViewModel = ViewModelProvider(this, viewModelFactory)[AddTaskViewModel::class.java]

        binding.addTvDueDate.setOnClickListener {
            showDatePicker(it)
        }

        binding.save.setOnClickListener {
            saveTask()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }


    private fun saveTask() {
        val title = binding.addEdTitle.text.toString()
        val description = binding.addEdDescription.text.toString()
        val reminder = binding.addTvDueDate.text.toString()

        when {
            title.isBlank() -> {
                // Show error or prompt user
                return
            }
            description.isBlank() -> {
                // Show error or prompt user
                return
            }
            reminder == getString(R.string.due_date) -> {
                // Show error or prompt user
                return
            }
            else -> {
                addTaskViewModel.insertTasks(Task(0, title, description, dueDateMillis))
                finish()
            }
        }
    }
    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.addTvDueDate.text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }
}