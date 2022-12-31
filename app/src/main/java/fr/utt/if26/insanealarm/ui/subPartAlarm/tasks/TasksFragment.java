package fr.utt.if26.insanealarm.ui.subPartAlarm.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.utt.if26.insanealarm.databinding.FragmentSubPartTaskBinding;
import fr.utt.if26.insanealarm.ui.addAlarm.AddAlarmViewModel;

public class TasksFragment extends Fragment {
    private FragmentSubPartTaskBinding binding;
    private AddAlarmViewModel alarmViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentSubPartTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NumberPicker npTaskNumber = binding.npTaskNumber;
        NumberPicker npTaskTimer = binding.npTaskTimer;

        npTaskNumber.setMinValue(0);
        npTaskNumber.setMaxValue(5);
        npTaskTimer.setMinValue(0);
        npTaskTimer.setMaxValue(120);

        return root;
    }
}
