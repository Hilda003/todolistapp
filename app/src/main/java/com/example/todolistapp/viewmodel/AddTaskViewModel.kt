package com.example.todolistapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.data.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    fun insertTasks(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }
}