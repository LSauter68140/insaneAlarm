package fr.utt.if26.insanealarm.ui.subPartAlarm.Snooze;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentSubPartSnoozeBinding;
import fr.utt.if26.insanealarm.ui.addAlarm.AddAlarmViewModel;

public class SnoozeFragment extends Fragment {
    private FragmentSubPartSnoozeBinding binding;
    private AddAlarmViewModel alarmViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentSubPartSnoozeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NumberPicker npSnoozeLimit = binding.npSnoozeLimit;
        NumberPicker npSnoozeTime = binding.npSnoozeTime;

        npSnoozeLimit.setMinValue(0);
        npSnoozeLimit.setMaxValue(10);
        npSnoozeTime.setMinValue(0);
        npSnoozeTime.setMaxValue(20);

        alarmViewModel.getSnoozeTime().observe(getViewLifecycleOwner(), npSnoozeTime::setValue);
        alarmViewModel.getSnoozeLimit().observe(getViewLifecycleOwner(), npSnoozeLimit::setValue);

        npSnoozeLimit.setOnValueChangedListener((picker, oldVal, newVal) -> alarmViewModel.getSnoozeLimit().setValue(newVal));
        npSnoozeTime.setOnValueChangedListener((picker, oldVal, newVal) -> alarmViewModel.getSnoozeTime().setValue(newVal));

        ConstraintLayout cntLytOptionalSttg = binding.constraintLayoutOptionalSettings;
        alarmViewModel.getActivateSnooze().observe(getViewLifecycleOwner(), activateSnooze -> {
            ((Switch) root.findViewById(R.id.switchActivateSnooze)).setChecked(activateSnooze);
            if (activateSnooze) {
                cntLytOptionalSttg.setVisibility(View.VISIBLE);
            } else {
                cntLytOptionalSttg.setVisibility(View.GONE);
            }
        });
        binding.layoutActivateSnooze.setOnClickListener(this::listenerActivateSnooze);

        return root;
    }

    public void listenerActivateSnooze(View v) {
        alarmViewModel.getActivateSnooze().setValue(Boolean.FALSE.equals(alarmViewModel.getActivateSnooze().getValue()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
