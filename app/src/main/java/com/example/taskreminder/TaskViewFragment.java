package com.example.taskreminder;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskreminder.databinding.FragmentTaskViewBinding;
import com.example.taskreminder.domain.model.Task;

import java.util.Objects;

public class TaskViewFragment extends Fragment {

    private TaskViewViewModel mViewModel;

    private FragmentTaskViewBinding binding;
    private long taskId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            System.out.println("Task ID: " + getArguments().getLong("taskId", -1));
            taskId = getArguments().getLong("taskId", -1); // Default value -1 if not found
        }

        binding = FragmentTaskViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

   @Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewModel = new ViewModelProvider(this).get(TaskViewViewModel.class);
    mViewModel.getTask(taskId).observe(getViewLifecycleOwner(), task -> {
        if (task != null) {
            binding.taskDescriptionEditText.setText(task.getDescription());
            binding.taskTitleEditText.setText(task.getTitle());
            binding.taskCompletedCheckbox.setChecked(task.isCompleted());
        }
    });


       TextWatcher textWatcher = new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               if (!Objects.requireNonNull(binding.taskTitleEditText.getText()).toString().isEmpty() ||
                       !Objects.requireNonNull(binding.taskDescriptionEditText.getText()).toString().isEmpty()) {
                   binding.updateButton.setVisibility(View.VISIBLE);
               } else {
                   binding.updateButton.setVisibility(View.GONE);
               }
           }

           @Override
           public void afterTextChanged(Editable s) {
           }
       };

       binding.taskTitleEditText.addTextChangedListener(textWatcher);
       binding.taskDescriptionEditText.addTextChangedListener(textWatcher);


    // Set click listener for the update button
    binding.updateButton.setOnClickListener(v -> {
        String newTitle = Objects.requireNonNull(binding.taskTitleEditText.getText()).toString();
        String newDescription = Objects.requireNonNull(binding.taskDescriptionEditText.getText()).toString();
        boolean isCompleted = binding.taskCompletedCheckbox.isChecked();

        // Update the task
        var taskDatetime = Objects.requireNonNull(mViewModel.getTask(taskId).getValue()).getDueDateMillis();
        var task = new Task(taskId, newTitle, newDescription, isCompleted, taskDatetime);
        mViewModel.updateTask(task);
        findNavController(this).navigateUp();
    });

    // Set click listener for the delete button
    binding.deleteButton.setOnClickListener(v -> {
        // Delete the task
        var task = mViewModel.getTask(taskId).getValue();
        mViewModel.deleteTask(task);
        findNavController(this).navigateUp();
    });
}


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}