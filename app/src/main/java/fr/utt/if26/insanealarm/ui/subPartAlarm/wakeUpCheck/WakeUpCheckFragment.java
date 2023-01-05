package fr.utt.if26.insanealarm.ui.subPartAlarm.wakeUpCheck;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.utt.if26.insanealarm.databinding.FragmentSubPartWakeupcheckBinding;
import fr.utt.if26.insanealarm.ui.addAlarm.AddAlarmViewModel;

public class WakeUpCheckFragment extends Fragment {

    FragmentSubPartWakeupcheckBinding binding;
    AddAlarmViewModel alarmViewModel;
    NumberPicker npDelayAfterDismiss;
    NumberPicker npCountdonwTime;
    LinearLayout layoutActivateWkupChck;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentSubPartWakeupcheckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        npCountdonwTime = binding.npTCountdownTime;
        npDelayAfterDismiss = binding.npDelayAfterDismiss;
        layoutActivateWkupChck = binding.layoutActivateWkupChck;

        npDelayAfterDismiss.setMinValue(0);
        npDelayAfterDismiss.setMaxValue(300);
        npCountdonwTime.setMinValue(0);
        npCountdonwTime.setMaxValue(180);

        // listener
        layoutActivateWkupChck.setOnClickListener(v ->
                alarmViewModel.getWkupChkIsActivate().setValue(
                        Boolean.FALSE.equals(alarmViewModel.getWkupChkIsActivate().getValue())
                )
        );
        npDelayAfterDismiss.setOnValueChangedListener((picker, oldVal, newVal) -> {
            alarmViewModel.getWkupChkDelayAfterDismiss().setValue(newVal);
        });
        npCountdonwTime.setOnValueChangedListener((picker, oldVal, newVal) -> {
            alarmViewModel.getWkupChkCountdownTime().setValue(newVal);
        });

        // observer
        alarmViewModel.getWkupChkIsActivate().observe(
                getViewLifecycleOwner(), isActivate -> {
                    ((Switch) binding.switchActivateWkupChck).setChecked(isActivate);
                    if (isActivate) {
                        binding.layoutNp.setVisibility(View.VISIBLE);
                    } else {
                        binding.layoutNp.setVisibility(View.GONE);
                    }
                }
        );
        alarmViewModel.getWkupChkCountdownTime().observe(getViewLifecycleOwner(), npCountdonwTime::setValue);
        alarmViewModel.getWkupChkDelayAfterDismiss().observe(getViewLifecycleOwner(), npDelayAfterDismiss::setValue);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
