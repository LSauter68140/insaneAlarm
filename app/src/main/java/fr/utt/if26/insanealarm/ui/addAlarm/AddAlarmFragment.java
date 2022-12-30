package fr.utt.if26.insanealarm.ui.addAlarm;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentAddEditBinding;
import fr.utt.if26.insanealarm.utils.DayTimeTranslator;
import fr.utt.if26.insanealarm.utils.FileManager;

public class AddAlarmFragment extends Fragment {

    private View root;
    private FragmentAddEditBinding binding;
    private TimePicker timePickerAddAlarm;
    private TextView tvNextGooff;
    private Boolean[] nextDayGooff;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //AddAlarmViewModel addAlarmViewModel =
        //        new ViewModelProvider(this).get(AddAlarmViewModel.class);
        AddAlarmViewModel addAlarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentAddEditBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        tvNextGooff = root.findViewById(R.id.tvNextGooff);
        timePickerAddAlarm = root.findViewById(R.id.timePickerAddAlarm);
        timePickerAddAlarm.setIs24HourView(true);
        timePickerAddAlarm.setOnTimeChangedListener((view, hour, minute) -> setNextGoOffCountDown(hour, minute));
        addBtnFrequency();

        // layout button part
        root.findViewById(R.id.layoutRingtone).setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_nav_addEditAlarm_to_nav_soundAlarm)); // open new fragment to change ringtone
        root.findViewById(R.id.layoutSnooze).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.nav_snoozeAlarm); // open new fragment to add snooze
        });
        root.findViewById(R.id.layoutDismiss).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.nav_dismissAlarm); // open new fragment to add snooze

        });
        root.findViewById(R.id.layoutSound).setOnClickListener(v -> {
            Log.i("ringtone", addAlarmViewModel.getAlarmLabel().getValue());
            addAlarmViewModel.getRingtoneName().setValue("/apero.mp3");
        });


        // toolbar buttons
        root.findViewById(R.id.discardBtn).setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp());
        root.findViewById(R.id.previewBtn).setOnClickListener(v -> {
            //TODO
        });

        addAlarmViewModel.getRingtoneName().observe(getViewLifecycleOwner(), ringtone ->
                ((TextView) root.findViewById(R.id.tvSoundDescription))
                        .setText(FileManager.getNameFromURI(requireContext(), Uri.parse(ringtone)))
        );

        ((EditText) root.findViewById(R.id.etAlarmLabel)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addAlarmViewModel.getAlarmLabel().setValue(s.toString());
            }
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
        nextDayGooff = new Boolean[]{false, false, false, false, false, false, false};
    }

    public void checkBoxListener(View v) {
        CheckBox clicked = (CheckBox) v;
        int index = Integer.parseInt((String) clicked.getHint());
        nextDayGooff[index] = clicked.isChecked();
        setNextGoOffCountDown(timePickerAddAlarm.getHour(), timePickerAddAlarm.getMinute());
    }

    @SuppressLint("SetTextI18n")
    public void setNextGoOffCountDown(int hourGoOff, int minuteGoOff) {

        LocalTime timeGoOff = LocalTime.parse(DayTimeTranslator.readableTime((long) hourGoOff, (long) minuteGoOff, 0L));
        int hourCurrent = LocalTime.now().getHour();
        int minuteCurrent = LocalTime.now().getMinute();
        LocalTime timeCurrent = LocalTime.parse(DayTimeTranslator.readableTime((long) hourCurrent, (long) minuteCurrent, 0L));
        Duration totalDuration = Duration.between(timeCurrent, timeGoOff);

        int currentDay = LocalDate.now().getDayOfWeek().getValue();
        int index = currentDay;
        // check next day
        do {
            index += 1;
            index = index % 7;
            Log.i("index", String.valueOf(index));
        } while (index != currentDay && !nextDayGooff[index]);
        //compute day between
        int dayBetween = ((index - currentDay) + 7) % 7;
        totalDuration = totalDuration.plusDays(dayBetween);

        // convert into readable time;
        if (totalDuration.toMinutes() < 0) {
            if (nextDayGooff[currentDay]) {
                // add a week if the time is done and we want an alarm the next week and only this day
                totalDuration = totalDuration.plusDays(7);
            } else {
                totalDuration = totalDuration.plusDays(1);
            }
        }
        long minutes = (totalDuration.toMinutes()) % 60;
        long hour = (totalDuration.toHours()) % 24;
        long day = totalDuration.toDays();

        tvNextGooff.setText(day + "j " + hour + "h " + minutes + "m");
    }

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