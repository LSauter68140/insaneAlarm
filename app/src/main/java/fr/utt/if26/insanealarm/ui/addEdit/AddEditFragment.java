package fr.utt.if26.insanealarm.ui.addEdit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentAddEditBinding;
import fr.utt.if26.insanealarm.utils.DayTimeTranslator;

public class AddEditFragment extends Fragment {

    private View root;
    private FragmentAddEditBinding binding;
    private TimePicker timePickerAddAlarm;
    private TextView tvNextGooff;
    // private HashMap<String, Boolean> nextDayGooff;
    private Boolean[] nextDayGooff;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddEditViewModel addEditViewModel =
                new ViewModelProvider(this).get(AddEditViewModel.class);

        binding = FragmentAddEditBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        tvNextGooff = root.findViewById(R.id.tvNextGooff);
        timePickerAddAlarm = root.findViewById(R.id.timePickerAddAlarm);
        timePickerAddAlarm.setIs24HourView(true);
        timePickerAddAlarm.setOnTimeChangedListener((view, hour, minute) -> setNextGoOffCountDown(hour, minute));
        addBtnFrequency();

        return root;
    }

    public void addBtnFrequency() {

        LinearLayout layoutFrequencyBtn = root.findViewById(R.id.layoutFrequencyBtn);
        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1
                );
        // String[] days = {"Lu", "Ma", "Me", "Je", "Ve", "Sa", "Su"};
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