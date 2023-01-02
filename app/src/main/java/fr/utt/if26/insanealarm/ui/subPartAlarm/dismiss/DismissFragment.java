package fr.utt.if26.insanealarm.ui.subPartAlarm.dismiss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentSubPartDismissBinding;
import fr.utt.if26.insanealarm.ui.addAlarm.AddAlarmViewModel;

public class DismissFragment extends Fragment {
    private FragmentSubPartDismissBinding binding;
    private AddAlarmViewModel alarmViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentSubPartDismissBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /// button layout

        root.findViewById(R.id.layoutControl).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.nav_controlAlarm); // open new fragment to add snooze
        });
        root.findViewById(R.id.layoutTask).setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("parentFragment", "Dismiss");
            bundle.putString("title", getString(R.string.titleDismissSnooze));
            Navigation.findNavController(requireView()).navigate(R.id.nav_taskAlarm, bundle);
        });


        //dimiss layout
        binding.layoutAutoDismiss.setOnClickListener(this::listenerAutoDismiss);
        alarmViewModel.getAutoDismiss().observe(getViewLifecycleOwner(), this::observationAutoDismiss);

        // numberPicker

        NumberPicker npAutoDismissMin = binding.npAutoDismissMin;
        NumberPicker npAutoDismissSec = binding.npAutoDismissSec;

        npAutoDismissMin.setMinValue(0);
        npAutoDismissMin.setMaxValue(99);
        npAutoDismissSec.setMinValue(0);
        npAutoDismissSec.setMaxValue(59);

        npAutoDismissMin.setOnValueChangedListener(this::npOnValueChanged);
        npAutoDismissSec.setOnValueChangedListener(this::npOnValueChanged);


        // better performance compare to update value after each notify with alarmViewModel.****.observe()
        Integer defaultAlarmViewModelValue = alarmViewModel.getMaxTimeSecAutoDismiss().getValue();
        if (defaultAlarmViewModelValue == null) {
            defaultAlarmViewModelValue = 0;
        }

        npAutoDismissMin.setValue(defaultAlarmViewModelValue / 60);
        npAutoDismissSec.setValue(defaultAlarmViewModelValue % 60);


        alarmViewModel.getAutoDismiss().observe(getViewLifecycleOwner(), this::observationAutoDismiss);
        return root;
    }

    public void listenerAutoDismiss(View v) {
        alarmViewModel.getAutoDismiss().setValue(Boolean.FALSE.equals(alarmViewModel.getAutoDismiss().getValue()));
    }

    public void observationAutoDismiss(Boolean autoDismiss) {
        ((Switch) binding.getRoot().findViewById(R.id.switchAutoDismiss)).setChecked(autoDismiss);
        if (autoDismiss) {
            binding.layoutAutoDismissSttng.setVisibility(View.VISIBLE);
        } else {
            binding.layoutAutoDismissSttng.setVisibility(View.GONE);

        }
    }

    public void npOnValueChanged(NumberPicker picker, Integer oldVal, Integer newVal) {
        // 2131231318 // min
        Integer oldAlarmValue = alarmViewModel.getMaxTimeSecAutoDismiss().getValue();
        if (oldAlarmValue == null) {
            oldAlarmValue = 0;
        }
        Integer timeConverter;
        int oldAlarmConverter;

        // id of sec np picker
        if (String.valueOf(picker.getId()).equals("2131231319")) {
            //require minute
            oldAlarmConverter = (oldAlarmValue / 60) * 60; // because of integer wrap
            timeConverter = 1;
        } else {
            // require second
            oldAlarmConverter = oldAlarmValue % 60;
            timeConverter = 60;
        }

        alarmViewModel.getMaxTimeSecAutoDismiss()
                .setValue(newVal * timeConverter + oldAlarmConverter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
