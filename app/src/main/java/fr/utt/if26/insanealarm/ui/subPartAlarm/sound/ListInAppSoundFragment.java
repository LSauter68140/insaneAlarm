package fr.utt.if26.insanealarm.ui.subPartAlarm.sound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.Arrays;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentAdapterListBinding;

public class ListInAppSoundFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fr.utt.if26.insanealarm.databinding.FragmentAdapterListBinding binding = FragmentAdapterListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Field[] rawFiles = R.raw.class.getFields();
        ListInAppSoundAdapter listInAppSoundAdapter = new ListInAppSoundAdapter(Arrays.asList(rawFiles), requireActivity(), this);

        RecyclerView localSoundRecyclerView = binding.localSoundRecyclerView;
        localSoundRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        localSoundRecyclerView.setAdapter(listInAppSoundAdapter);


        return root;
    }
}
