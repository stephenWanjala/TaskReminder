package com.example.taskreminder;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskreminder.databinding.FragmentAddTaskBinding;
import com.example.taskreminder.domain.model.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class AddTaskFragment extends Fragment {

    private FragmentAddTaskBinding binding;
    private Calendar dueDateCalendar = Calendar.getInstance();
    private TaskViewModel taskViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.iconClickable.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, year, month, dayOfMonth) -> {
                        dueDateCalendar.set(Calendar.YEAR, year);
                        dueDateCalendar.set(Calendar.MONTH, month);
                        dueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                (view2, hourOfDay, minute) -> {
                                    dueDateCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    dueDateCalendar.set(Calendar.MINUTE, minute);

                                    long dueDateMillis = dueDateCalendar.getTimeInMillis();
                                    Toast.makeText(getContext(), "Due date set to: " + dueDateMillis, Toast.LENGTH_SHORT).show();
                                    binding.pickDueDate.setText(dueDateCalendar.getTime().toString());
                                    if (!Objects.requireNonNull(binding.taskDescription.getText()).toString().isEmpty() && !Objects.requireNonNull(binding.taskTitle.getText()).toString().isEmpty() &&
                                            !Objects.requireNonNull(binding.pickDueDate.getText()).toString().isEmpty()){
                                        binding.saveTask.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.saveTask.setVisibility(View.GONE);
                                    }
                                }, dueDateCalendar.get(Calendar.HOUR_OF_DAY), dueDateCalendar.get(Calendar.MINUTE), true);
                        timePickerDialog.show();
                    }, dueDateCalendar.get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH), dueDateCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        if (!Objects.requireNonNull(binding.taskDescription.getText()).toString().isEmpty() && !Objects.requireNonNull(binding.taskTitle.getText()).toString().isEmpty() &&
                !Objects.requireNonNull(binding.pickDueDate.getText()).toString().isEmpty()){
            binding.saveTask.setVisibility(View.VISIBLE);
        } else {
            binding.saveTask.setVisibility(View.GONE);
        }


        binding.saveTask.setOnClickListener(v -> {
            String taskTitle = Objects.requireNonNull(binding.taskTitle.getText()).toString();
            String taskDescription = binding.taskDescription.getText().toString();
            long dueDate = dueDateCalendar.getTimeInMillis();
            Task task = new Task(0,taskTitle, taskDescription,false, dueDate);

            taskViewModel.insertTask(task);
            Toast.makeText(getContext(), "Task saved", Toast.LENGTH_SHORT).show();
            findNavController(this).navigateUp();
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}