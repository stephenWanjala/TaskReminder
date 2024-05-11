package com.example.taskreminder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.taskreminder.databinding.FragmentHomeBinding;
import com.example.taskreminder.domain.model.Task;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TaskViewModel taskViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
//        Task newTask = new Task(0,"Example Task", "This is an example task", false, System.currentTimeMillis());
//        taskViewModel.insertTask(newTask);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}