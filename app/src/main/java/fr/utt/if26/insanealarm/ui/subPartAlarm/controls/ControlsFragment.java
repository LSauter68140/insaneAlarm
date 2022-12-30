package fr.utt.if26.insanealarm.ui.subPartAlarm.controls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
