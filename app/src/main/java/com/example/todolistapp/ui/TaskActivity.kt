package com.example.todolistapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.utils.Event
import com.example.todolistapp.databinding.ActivityTaskBinding
import com.example.todolistapp.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.Observer
import com.example.todolistapp.R
import com.example.todolistapp.TaskAdapter
import com.example.todolistapp.ViewModelFactory

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }


        taskAdapter = TaskAdapter { task, isChecked ->
            taskViewModel.completeTask(task, isChecked)
        }

        binding.rvTask.apply {
            layoutManager = LinearLayoutManager(this@TaskActivity)
            adapter = taskAdapter
        }

        initAction()

        val viewModelFactory = ViewModelFactory.getInstance(this)
        taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        taskViewModel.tasks.observe(this, Observer(this::showRecyclerView))

        // Fixing bug: snackBar not show when task completed
        taskViewModel.snackbarText.observe(this, Observer(this::showSnackBar))


    }
    private fun showRecyclerView(task: PagedList<Task>) {
        // Submit pagedList to adapter and update database when onCheckChange
        taskAdapter.submitList(task)
    }

    private fun showSnackBar(eventMessage: Event<Int>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            binding.coordinatorLayout,
            getString(message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

    private fun initAction() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = (viewHolder as TaskAdapter.TaskViewHolder).getTask
                taskViewModel.deleteTask(task)
            }

        })
        itemTouchHelper.attachToRecyclerView(binding.rvTask)
    }
}