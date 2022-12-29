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
import fr.utt.if26.insanealarm.utils.DayTimeTranslator;

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


    public void bind(Alarm alarm, AlarmViewModel alarmViewModel) {
        nextRingTime.setText(formatNextTimeToGoOff(alarm.getTimeToGoOff()));
        frequency.setText(formatFrequency(alarm, itemView.getResources()));
        activateAlarm.setChecked(alarm.getActivate());
        activateAlarm.setOnClickListener(v -> onClickAlarmSwitch(alarmViewModel, alarm));
        type.setText(String.valueOf(alarm.getDismiss().getTask().getNumberTask()));
        settingBtn.setOnClickListener(v -> onClickSettingBtn(v, alarmViewModel, alarm));
    }

    static AlarmViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.alarm_list_item, parent, false);
        return new AlarmViewHolder(view);
    }

    private String formatNextTimeToGoOff(LocalTime localTime) {
        return DayTimeTranslator.readableTime((long) localTime.getHour(), (long) localTime.getMinute());
    }

    private String formatFrequency(Alarm alarm, Resources resources) {
        ArrayList<String> validDays = alarm.getFrequency().getWeekSchedule();
        if (validDays.size() == 0) {
            return resources.getString(string.unique);
        }

        Boolean isShort = validDays.size() > 2;

        StringBuilder weekSummary = new StringBuilder();
        for (String validDay : validDays
        ) {
            weekSummary.append(DayTimeTranslator.getDay(validDay, isShort, resources)).append(",");
        }
        return weekSummary.substring(0, weekSummary.length() - 1);
    }

    // add listener function for each component

    public void onClickAlarmSwitch(AlarmViewModel alarmViewModel, Alarm alarm) {
        alarm.setActivate(activateAlarm.isChecked());
        alarmViewModel.updateAlarm(alarm);
        // test for animation
        /*
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(400);
                alarmViewModel.updateAlarm(alarm);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();*/
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickSettingBtn(View view, AlarmViewModel alarmViewModel, Alarm alarm) {
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
        // better style use windows popup
        // https://stackoverflow.com/questions/49706495/how-to-pass-a-custom-layout-to-a-popupmenu
        // https://stackoverflow.com/questions/34449251/implement-pop-up-menu-with-margin
        // https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android

        alarmViewModel.updateAlarm(alarm);
    }

}
