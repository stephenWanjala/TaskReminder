package com.example.taskreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskreminder.data.TaskAdapter;
import com.example.taskreminder.databinding.FragmentHomeBinding;
import com.example.taskreminder.domain.model.Task;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskAdapter = new TaskAdapter();
        binding.rvTasks.setAdapter(taskAdapter);
        binding.rvTasks.setLayoutManager(new LinearLayoutManager(requireContext()));



        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks.isEmpty()) {
                // Show empty state
                binding.rvTasks.setVisibility(View.GONE);
                binding.tvEmptyState.setVisibility(View.VISIBLE);

            } else {
                // Hide empty state
                binding.rvTasks.setVisibility(View.VISIBLE);
                binding.tvEmptyState.setVisibility(View.GONE);
            }
            taskAdapter.submitList(tasks); // Submit the new list of tasks to ListAdapter
        });

        // Example: Insert a new task
//        Task newTask = new Task(0,"Example Task", "This is an example task", false, System.currentTimeMillis()+1000000);
//        taskViewModel.insertTask(newTask);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
