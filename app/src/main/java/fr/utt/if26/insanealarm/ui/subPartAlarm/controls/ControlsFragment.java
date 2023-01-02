package fr.utt.if26.insanealarm.ui.subPartAlarm.controls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentSubPartControlsBinding;
import fr.utt.if26.insanealarm.ui.addAlarm.AddAlarmViewModel;

public class ControlsFragment extends Fragment {
    private FragmentSubPartControlsBinding binding;
    private AddAlarmViewModel alarmViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentSubPartControlsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // add observer and listener for dismiss and snooze toggle buttons
        Map<String, ToggleButton> allToggleBtns = Map.of(
                "getSnoozeCtrOnscreen", root.findViewById(R.id.tbOnScreenSnooze),
                "getSnoozeCtrVolume", root.findViewById(R.id.tbVolumeSnooze),
                "getSnoozeCtrPower", root.findViewById(R.id.tbPowerSnooze),
                "getSnoozeCtrShake", root.findViewById(R.id.tbShakeSnooze),
                "getDismissCtrOnscreen", root.findViewById(R.id.tbOnScreenDismiss),
                "getDismissCtrVolume", root.findViewById(R.id.tbVolumeDimiss),
                "getDismissCtrPower", root.findViewById(R.id.tbPowerDismiss),
                "getDismissCtrShake", root.findViewById(R.id.tbShakeDismiss)
        );

        for (Map.Entry<String, ToggleButton> toggleBtn : allToggleBtns.entrySet()) {
            toggleBtn.getValue().setOnClickListener(v -> tgOnClickListener(v, toggleBtn.getKey()));
            observerOnAlarmViewBoolean(toggleBtn.getKey(), toggleBtn.getValue(), getViewLifecycleOwner());
        }
        return root;
    }

    public void observerOnAlarmViewBoolean(String method, ToggleButton tgToChangeValue, androidx.lifecycle.LifecycleOwner g) {
        try {
            ((MutableLiveData<Boolean>)
                    alarmViewModel.getClass()
                            .getDeclaredMethod(method).invoke(alarmViewModel))
                    .observe(g, tgToChangeValue::setChecked);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void tgOnClickListener(View v, String method) {
        // call the method
        try {
            ((MutableLiveData<Boolean>)
                    alarmViewModel.getClass()
                            .getDeclaredMethod(method).invoke(alarmViewModel))
                    .setValue(!v.isSelected());

            // check for dismiss ctl if we have at least on dismiss control available
            if (method.contains("Dismiss") && alarmViewModel.atLeatOneDismissCrl()) {
                // we rollback we need at least one
                ((MutableLiveData<Boolean>)
                        alarmViewModel.getClass()
                                .getDeclaredMethod(method).invoke(alarmViewModel))
                        .setValue(v.isSelected());
                Toast.makeText(requireContext(),
                                "Impossible de l'enlever, on a besoin d'au moins un mode de désactivation",
                                Toast.LENGTH_SHORT)
                        .show();

            } else {
                v.setSelected(!v.isSelected());
            }


        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
