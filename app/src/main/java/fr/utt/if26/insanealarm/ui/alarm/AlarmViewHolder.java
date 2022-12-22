package fr.utt.if26.insanealarm.ui.alarm;

import static fr.utt.if26.insanealarm.R.id;
import static fr.utt.if26.insanealarm.R.layout;
import static fr.utt.if26.insanealarm.R.menu;
import static fr.utt.if26.insanealarm.R.string;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

    @SuppressLint("NonConstantResourceId")
    public AlarmViewHolder(@NonNull View itemView) {
        super(itemView);
        nextRingTime = itemView.findViewById(id.listItemNextRingTime);
        frequency = itemView.findViewById(id.listItemFrequency);
        settingBtn = itemView.findViewById(id.listItemSettingBtn);
        activateAlarm = itemView.findViewById(id.listItemActivateAlarm);
        type = itemView.findViewById(id.listItemType);


    }


    @SuppressLint("NonConstantResourceId")
    public void bind(Alarm alarm) {
        nextRingTime.setText(formatNextTimeToGoOff(alarm.getTimeToGoOff()));
        frequency.setText(formatFrequency(alarm, itemView.getResources()));
        activateAlarm.setChecked(alarm.getActivate());
        type.setText(String.valueOf(alarm.getAlarmType()));
        settingBtn.setOnClickListener(view ->
        {
            PopupMenu popupMenu = new PopupMenu(settingBtn.getContext(), settingBtn, R.style.alarmSettingPopupMenu);
            popupMenu.inflate(menu.alarm_setting_context);
            popupMenu.setGravity(Gravity.END);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case id.alarmSettingEdit:
                        Toast.makeText(settingBtn.getContext(), "Editer " + alarm.getName(), Toast.LENGTH_SHORT).show();
                        return true;
                    case id.alarmSettingPreview:
                        Toast.makeText(itemView.getContext(), "Visualiser", Toast.LENGTH_SHORT).show();

                        return true;
                    case id.alarmSettingDelete:
                        Toast.makeText(itemView.getContext(), "supprimer", Toast.LENGTH_SHORT).show();

                        return true;
                    default:
                        return false;
                }
            });
            //displaying the popup
            popupMenu.show();
        });
    }

    static AlarmViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.alarm_list_item, parent, false);
        return new AlarmViewHolder(view);
    }

    private String formatNextTimeToGoOff(LocalTime localTime) {
        return localTime.getHour() + ":" + localTime.getMinute();
    }

    private String formatFrequency(Alarm alarm, Resources resources) {
        ArrayList<String> validDays = alarm.getFrequency().getWeekSchedule();
        if (validDays.size() == 0) {
            return resources.getString(string.unique);
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
