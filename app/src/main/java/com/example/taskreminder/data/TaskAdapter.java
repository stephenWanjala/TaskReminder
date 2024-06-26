package com.example.taskreminder.data;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskreminder.R;
import com.example.taskreminder.databinding.TaskItemBinding;
import com.example.taskreminder.domain.model.Task;
import com.example.taskreminder.utils.Utils;

import java.util.Objects;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {

    public TaskAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskItemBinding binding = TaskItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = getItem(position);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskClick(task);
            }
        });
        // Check if the task is completed
        if (task.isCompleted()) {
            holder.binding.getRoot().setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.completed_color));
            holder.binding.getRoot().setCheckable(true);
            holder.binding.getRoot().setChecked(true);
        }
        // Check if the task is overdue
        else if (task.getDueDateMillis() < System.currentTimeMillis()) {
            holder.binding.getRoot().setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.overdue_color));
        }
        // Reset to default color for other tasks
        else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
        holder.bind(task);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final TaskItemBinding binding;

        TaskViewHolder(@NonNull TaskItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Task task) {
            binding.tvTaskTitle.setText(task.getTitle());
            binding.tvTaskDescription.setText(task.getDescription());
            // Use string resource with placeholder
            String dueDateText = binding.getRoot().getContext().getString(R.string.due_date, Utils.formatDate(task.getDueDateMillis()));
            binding.tvTaskDueDate.setText(dueDateText);
        }
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId(); // Check if items have the same ID
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            // Check if item contents are the same
            return Objects.equals(oldItem, newItem);
        }
    };

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    private OnTaskClickListener listener = null;

    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.listener = listener;
    }
}

