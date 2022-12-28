package fr.utt.if26.insanealarm.ui.subPartAlarm.Sound;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import fr.utt.if26.insanealarm.databinding.FragmentSubPartSoundBinding;
import fr.utt.if26.insanealarm.ui.addAlarm.AddAlarmViewModel;

public class SoundFragment extends Fragment {

    private FragmentSubPartSoundBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddAlarmViewModel alarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentSubPartSoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvSoundTest;
        final EditText editSound = binding.editSound;
        alarmViewModel.getRingtone().observe(getViewLifecycleOwner(), textView::setText);
        editSound.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                alarmViewModel.getRingtone().setValue(s.toString());
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
