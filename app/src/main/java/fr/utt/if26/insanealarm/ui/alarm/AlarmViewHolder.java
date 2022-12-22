package fr.utt.if26.insanealarm.ui.alarm;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.ArrayList;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.utils.DayTranslator;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class AlarmViewHolder extends RecyclerView.ViewHolder {

    private final TextView nextRingTime;
    private final TextView frequency;
    private final ImageButton settingBtn;
    private final Switch activateAlarm;
    private final TextView type;

    public AlarmViewHolder(@NonNull View itemView) {
        super(itemView);
        nextRingTime = itemView.findViewById(R.id.listItemNextRingTime);
        frequency = itemView.findViewById(R.id.listItemFrequency);
        settingBtn = itemView.findViewById(R.id.listItemSettingBtn);
        activateAlarm = itemView.findViewById(R.id.listItemActivateAlarm);
        type = itemView.findViewById(R.id.listItemType);
    }

    public void bind(Alarm alarm) {
        nextRingTime.setText(formatNextTimeToGoOff(alarm.getTimeToGoOff()));
        frequency.setText(formatFrequency(alarm, itemView.getResources()));
        activateAlarm.setChecked(alarm.getActivate());
        type.setText(String.valueOf(alarm.getAlarmType()));
    }

    static AlarmViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_list_item, parent, false);
        return new AlarmViewHolder(view);
    }

    private String formatNextTimeToGoOff(LocalTime localTime) {
        return localTime.getHour() + ":" + localTime.getMinute();
    }

    private String formatFrequency(Alarm alarm, Resources resources) {
        ArrayList<String> validDays = alarm.getFrequency().getWeekSchedule();
        if (validDays.size() == 0) {
            return resources.getString(R.string.unique);
        }

        Boolean isShort = validDays.size() < 3;

        StringBuilder weekSummary = new StringBuilder();
        for (String validDay : validDays
        ) {
            weekSummary.append(DayTranslator.getDay(validDay, isShort, resources)).append(",");
        }
        return weekSummary.substring(0, weekSummary.length() - 1);
    }
}
