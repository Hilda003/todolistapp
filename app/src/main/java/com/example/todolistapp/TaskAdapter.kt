package com.example.todolistapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.example.todolistapp.databinding.TaskItemBinding
import com.example.todolistapp.ui.DetailTaskActivity

class TaskAdapter(
    private val onCheckedChange: (Task, Boolean) -> Unit
) : PagedListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position) as Task
        holder.bind(task)
        when {
            task.isCompleted -> {
                holder.binding.itemCheckbox.isChecked = true
                holder.binding.itemTvTitle.state = TaskTitleView.DONE
            }
            task.dueDateMillis < System.currentTimeMillis() -> {
                holder.binding.itemCheckbox.isChecked = false
                holder.binding.itemTvTitle.state = TaskTitleView.OVERDUE
            }
            else -> {
                holder.binding.itemCheckbox.isChecked = false
                holder.binding.itemTvTitle.state = TaskTitleView.NORMAL
            }
        }
    }

    inner class TaskViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var getTask: Task

        fun bind(task: Task) {
            getTask = task
            binding.itemTvTitle.text = task.title
            binding.itemTvDate.text = DateConverter.convertMillisToString(task.dueDateMillis)
            itemView.setOnClickListener {
                val detailIntent = Intent(itemView.context, DetailTaskActivity::class.java)
                detailIntent.putExtra(TASK_ID, task.id)
                itemView.context.startActivity(detailIntent)
            }
            binding.itemCheckbox.setOnClickListener {
                onCheckedChange(task, !task.isCompleted)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }
}
