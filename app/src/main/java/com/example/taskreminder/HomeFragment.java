package com.example.taskreminder;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        handleTaskClick();
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.rvTasks);



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

//         Example: Insert a new task now plus 12mins
//        Task newTask = new Task(0,"Example Task", "This is an example task", false, System.currentTimeMillis()+720000);
//        taskViewModel.insertTask(newTask);

        binding.fab.setOnClickListener(v -> findNavController(this).navigate(R.id.AddTaskFragment));
    }

 private  void handleTaskClick() {
    taskAdapter.setOnTaskClickListener(task -> {
        // Handle task click
        Bundle args = new Bundle();
        args.putLong("taskId", task.getId()); // Use "taskId" instead of "taskid"
        findNavController(this).navigate(R.id.action_TasksListFragment_to_taskViewFragment, args);
        // You can navigate to another fragment and pass the taskid as an argument
    });
}

ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT) {

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        Task task = taskAdapter.getCurrentList().get(position);
        taskViewModel.deleteTask(task);
    }
};

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
