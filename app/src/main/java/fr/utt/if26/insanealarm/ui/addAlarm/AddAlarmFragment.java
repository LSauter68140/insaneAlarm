package fr.utt.if26.insanealarm.ui.addAlarm;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentAddEditBinding;
import fr.utt.if26.insanealarm.room.model.Alarm;
import fr.utt.if26.insanealarm.ui.alarm.AlarmViewModel;
import fr.utt.if26.insanealarm.taskBackground.utils.AlarmUtils;
import fr.utt.if26.insanealarm.taskBackground.utils.DayTimeTranslator;
import fr.utt.if26.insanealarm.taskBackground.utils.FileManager;

public class AddAlarmFragment extends Fragment {

    private View root;
    private FragmentAddEditBinding binding;
    private TimePicker timePickerAddAlarm;
    private TextView tvNextGooff;
    private Boolean[] nextDayGooff;
    private AddAlarmViewModel addAlarmViewModel;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        AlarmViewModel alarmViewModel =
                new ViewModelProvider(requireActivity()).get(AlarmViewModel.class);
        addAlarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);

        binding = FragmentAddEditBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        if (getArguments() != null) {
            Alarm alarm;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                alarm = getArguments().getSerializable("alarmToEdit", Alarm.class);
            } else {
                alarm = (Alarm) getArguments().getSerializable("alarmToEdit");
            }
            if (!alarm.getId().equals(Objects.requireNonNull(addAlarmViewModel.getAlarm().getValue()).getId()))
                addAlarmViewModel.addExistingAlarm(alarm);
        }


        tvNextGooff = root.findViewById(R.id.tvNextGooff);
        timePickerAddAlarm = root.findViewById(R.id.timePickerAddAlarm);
        timePickerAddAlarm.setIs24HourView(true);
        timePickerAddAlarm.setHour(Objects.requireNonNull(addAlarmViewModel.getNextRing().getValue()).getHour());
        timePickerAddAlarm.setMinute(Objects.requireNonNull(addAlarmViewModel.getNextRing().getValue()).getMinute());
        timePickerAddAlarm.setOnTimeChangedListener((view, hour, minute) -> setNextGoOffCountDown(hour, minute));
        addBtnFrequency();

        // layout button part
        root.findViewById(R.id.layoutRingtone).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.nav_soundAlarm);// open new fragment to change ringtone
        });
        root.findViewById(R.id.layoutSnooze).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.nav_snoozeAlarm); // open new fragment to add snooze
        });
        root.findViewById(R.id.layoutDismiss).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.nav_dismissAlarm); // open new fragment to add dismiss

        });
        root.findViewById(R.id.layoutWakeupCheck).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.nav_wakeupCheckAlarm); // open new fragment to add wakeUpCheck
        });


        // toolbar buttons
        root.findViewById(R.id.discardBtn).setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp());

        addAlarmViewModel.getRingtoneName().observe(getViewLifecycleOwner(), ringtone ->
                ((TextView) root.findViewById(R.id.tvSoundDescription))
                        .setText(FileManager.getNameFromURI(requireContext(), Uri.parse(ringtone)))
        );


        addAlarmViewModel.getAlarmFrequencyDay().observe(getViewLifecycleOwner(), frequencyDay -> {
            int index = 0;
            nextDayGooff = new Boolean[frequencyDay.size()];
            nextDayGooff = frequencyDay.toArray(nextDayGooff);
            for (Boolean activateDay : frequencyDay) {
                ((CheckBox) binding.layoutFrequencyBtn.getChildAt(index)).setChecked(activateDay);
                index++;
            }
        });
        addAlarmViewModel.getNextRing().observe(getViewLifecycleOwner(), nextRing -> {
            Duration totalDuration = Duration.between(LocalDateTime.now(), nextRing);
            long minutes = (totalDuration.toMinutes()) % 60;
            long hour = (totalDuration.toHours()) % 24;
            long day = totalDuration.toDays();
            tvNextGooff.setText(day + "j " + hour + "h " + minutes + "m");
        });
        addAlarmViewModel.getAlarmName().observe(
                getViewLifecycleOwner(),
                alarmName -> observerTextView(((EditText) root.findViewById(R.id.editAlarmName)), alarmName)
        );
        addAlarmViewModel.getAlarmLabel().observe(
                getViewLifecycleOwner(),
                alarmLabel -> observerTextView(((EditText) root.findViewById(R.id.etAlarmLabel)), alarmLabel)
        );
        ((EditText) root.findViewById(R.id.editAlarmName)).addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(addAlarmViewModel.getAlarmName().getValue()))
                    addAlarmViewModel.getAlarmName().setValue(s.toString());
            }
        });
        ((EditText) root.findViewById(R.id.etAlarmLabel)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(addAlarmViewModel.getAlarmLabel().getValue()))
                    addAlarmViewModel.getAlarmLabel().setValue(s.toString());
            }
        });
        binding.addAlarmBtn.setOnClickListener(v -> {
            addAlarmViewModel.getFinalAlarm().display();
            alarmViewModel.insert(addAlarmViewModel.getFinalAlarm());

            NavHostFragment.findNavController(this).navigateUp();
        });

        return root;
    }

    public void addBtnFrequency() {

        LinearLayout layoutFrequencyBtn = root.findViewById(R.id.layoutFrequencyBtn);
        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1
                );
        String[] days = {"mo", "tu", "we", "th", "fr", "sa", "su"};
        // alarmViewModel state
        CheckBox checkBox;
        int index = 0;
        for (String day : days) {
            checkBox = new CheckBox(getActivity());
            checkBox.setHint(String.valueOf(index));
            checkBox.setLayoutParams(buttonParams);
            checkBox.setText(DayTimeTranslator.getDay(day, true, getResources()));
            checkBox.setBackground(
                    ContextCompat.getDrawable(requireContext(), R.drawable.control_switch_background_middle)
            );
            checkBox.setButtonDrawable(null);
            checkBox.setGravity(Gravity.CENTER);
            checkBox.setOnClickListener(this::checkBoxListener);
            layoutFrequencyBtn.addView(checkBox);
            index++;
        }
    }

    public void checkBoxListener(View v) {
        CheckBox clicked = (CheckBox) v;
        int index = Integer.parseInt((String) clicked.getHint());
        nextDayGooff[index] = !nextDayGooff[index];
        addAlarmViewModel.getAlarmFrequencyDay().setValue(new ArrayList<>(Arrays.asList(nextDayGooff)));
        setNextGoOffCountDown(timePickerAddAlarm.getHour(), timePickerAddAlarm.getMinute());
    }

    public void setNextGoOffCountDown(int hourGoOff, int minuteGoOff) {
        LocalTime timeGoOff = LocalTime.parse(DayTimeTranslator.readableTime((long) hourGoOff, (long) minuteGoOff, 0L));
        Duration totalDuration = AlarmUtils.getDurationNextGoOff(timeGoOff, nextDayGooff);
        addAlarmViewModel.getNextRing().setValue(LocalDateTime.now().plus(totalDuration));
    }

    public void observerTextView(EditText editText, String newValue) {
        editText.setText(newValue);
        editText.setSelection(newValue.length());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}